package com.example.mysharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.mysharedpreferences.databinding.ActivityFormUserPreferenceBinding

class FormUserPreferenceActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFormUserPreferenceBinding

    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormUserPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener(this)

        /**
         * kode dibawah adalah untuk mengambil data dari intent
         */
        userModel = intent.getParcelableExtra<UserModel>("USER") as UserModel
        val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)

        var actionBarTitle = ""
        var btnTitle = ""

        when(formType){
            /**
             * jika TYPE_ADD yang dikirim dari MainActivity maka:
             */
            TYPE_ADD -> {
                actionBarTitle = "Tambah Baru"
                btnTitle = "Simpan"
            }
            /**
             * jika TYPE_ADD yang dikirim dari MainActivity maka:
             */
            TYPE_EDIT -> {
                actionBarTitle = "Ubah"
                btnTitle = "Update"
                showPreferenceInForm()
            }
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.text = btnTitle

    }



    override fun onClick(view: View) {
        if (view.id == R.id.btn_save){
            /**
             * mengambil data yang diinput user
             */
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val phoneNo = binding.edtPhone.text.toString().trim()
            val isLoveMU = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes

            /**
             * validasi form input
             */
            if (name.isEmpty()) {
                binding.edtName.error = FIELD_REQUIRED
                return
            }

            if (email.isEmpty()) {
                binding.edtEmail.error = FIELD_REQUIRED
                return
            }

            if (!isValidEmail(email)) {
                binding.edtEmail.error = FIELD_IS_NOT_VALID
                return
            }

            if (age.isEmpty()) {
                binding.edtAge.error = FIELD_REQUIRED
                return
            }

            if (phoneNo.isEmpty()) {
                binding.edtPhone.error = FIELD_REQUIRED
                return
            }

            /**
             * memastikan yang diinput user hanyalah angka
             */
            if (!TextUtils.isDigitsOnly(phoneNo)) {
                binding.edtPhone.error = FIELD_DIGIT_ONLY
                return
            }

            saveUser(name, email, age, phoneNo, isLoveMU)

            /**
             * mengarahkan ke halaman mainactivity
             */
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_RESULT, userModel)
            /**
             * mengirimkan data saat intent ke mainactivity
             *
             */
            setResult(RESULT_CODE, resultIntent)

            /**
             * Fungsi finish() di Kotlin Android Studio digunakan untuk menutup activity atau fragment
             * yang sedang aktif saat ini. Ketika kita memanggil fungsi finish(), maka activity atau
             * fragment tersebut akan dihancurkan atau dihapus dari stack activity atau fragment,
             * dan user akan kembali ke activity atau fragment sebelumnya.
             *
             * Fungsi finish() biasanya dipanggil ketika kita ingin menutup activity atau fragment
             * saat user menyelesaikan suatu tugas atau ketika kita ingin memulai activity atau fragment baru.
             * Contohnya, ketika kita ingin kembali ke activity atau fragment sebelumnya setelah selesai melakukan
             * suatu tugas seperti mengisi form atau memilih item dari sebuah list.
             *
             * Pemanggilan fungsi finish() juga dapat dilakukan dengan memberikan parameter resultCode
             * dan Intent jika activity atau fragment saat ini dijalankan dengan startActivityForResult().
             * Dalam hal ini, resultCode dan Intent akan dikirim kembali ke activity atau fragment pemanggil.
             */
            finish()

        }
    }

    /**
     * fungsi untuk menyimpan data yang diinput user
     */
    private fun saveUser(name: String, email: String, age: String, phoneNo: String, isLoveMU: Boolean) {
        val userPreference = UserPreference(this)

        /**
         * menyimpan ke userModel
         */
        userModel.name = name
        userModel.email = email
        userModel.age = Integer.parseInt(age)
        userModel.phoneNumber = phoneNo
        userModel.isLove = isLoveMU

        userPreference.setUser(userModel)
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
    }

    /**
     * fungsi untuk mengecek apakah email yang diinput valid atau idak, Bentuk email yang valid yaitu xxx@xxx.xxx.
     */
    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * menampilkan data yang telah tersimpan sebelumnya
     */
    private fun showPreferenceInForm() {
        binding.edtName.setText(userModel.name)
        binding.edtEmail.setText(userModel.email)
        binding.edtAge.setText(userModel.age.toString())
        binding.edtPhone.setText(userModel.phoneNumber)
        if (userModel.isLove) {
            binding.rbYes.isChecked = true
        } else {
            binding.rbNo.isChecked = true
        }
    }

    /**
     * fungsi untuk membuat agar FormUserPreferenceActivity bisa kembali ke MainActivity
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val EXTRA_TYPE_FORM = "extra_type_form"
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101

        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
        private const val FIELD_DIGIT_ONLY = "Hanya boleh terisi numerik"
        private const val FIELD_IS_NOT_VALID = "Email tidak valid"
    }
}