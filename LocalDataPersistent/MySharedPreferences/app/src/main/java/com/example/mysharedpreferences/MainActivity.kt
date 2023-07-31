package com.example.mysharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mysharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mUserPreference: UserPreference

    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel

    /**
     * untuk memulai activity kita tidak menggunakan startActivity, melainkan menggunakan metode
     * launch dari ActivityResultLauncher hasil dari registerForActivityForResult. Apa bedanya?
     * Bedanya yaitu registerForActivityResult tidak hanya berpindah Activity, namun juga mendapatkan
     * result (hasil) dari Activity tersebut.
     *
     * resultLauncher digunakan untuk menerima data yang dikirimkan oleh FormUserPreferenceActivity dengan
     * cara menyamakan resultCodenya
     */
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.data != null && result.resultCode == FormUserPreferenceActivity.RESULT_CODE) {
            userModel = result.data?.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel
            populateView(userModel)
            checkForm(userModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My User Preference"

        mUserPreference = UserPreference(this)

        showExistingPreference()

        binding.btnSave.setOnClickListener(this)
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        populateView(userModel)
        checkForm(userModel)
    }

    /**
     * fungsi untuk menampilkan data user, jika kosong maka tampikan tulisan "tidak ada"
     */
    private fun populateView(userModel: UserModel) {
        binding.tvName.text = if (userModel.name.toString().isEmpty()) "Tidak Ada" else userModel.name
        binding.tvAge.text = if (userModel.age.toString().isEmpty()) "Tidak Ada" else userModel.age.toString()
        binding.tvIsLoveMu.text = if (userModel.isLove) "Ya" else "Tidak"
        binding.tvEmail.text = if (userModel.email.toString().isEmpty()) "Tidak Ada" else userModel.email
        binding.tvPhone.text = if (userModel.phoneNumber.toString().isEmpty()) "Tidak Ada" else userModel.phoneNumber
    }

    /**
     * fungsi untuk mengecek apakah sudah ada data yang tersimpan pada sharedPreferences
     */
    private fun checkForm(userModel: UserModel) {
        when {
            /**
             * jika sudah ada data maka tulisan button berubah menjadi "ubah" dan bikin variable
             * isPreferenceEmpty menjadi false
             */
            userModel.name.toString().isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            /**
             * jika kosong maka ubah tulisan button menjadi "simpan" dan bikin variable isPrefecence menjadi true
             */
            else -> {
                binding.btnSave.text = getString(R.string.save)
                isPreferenceEmpty = true
            }
        }
    }

    override fun onClick(view: View) {
        /**
         * jika button save di click maka fungsi di dalamnya dijalankan
         */
        if (view.id == R.id.btn_save){
            val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
            when {
                /**
                 * jika udah ada data yng tersimpan di sharedPreferences
                 */
                isPreferenceEmpty -> {
                    intent.putExtra(
                        /**
                         * mengirim data ke formUserPreferenceActivity dengan type add
                         */
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_ADD
                    )
                    intent.putExtra("USER", userModel)
                }
                /**
                 * jika belum ada data yng tersimpan di sharedPreferences
                 */
                else -> {
                    intent.putExtra(
                        /**
                         * mengirim data ke formUserPreferenceActivity dengan type edit
                         */
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_EDIT
                    )
                    intent.putExtra("USER", userModel)
                }
            }
            resultLauncher.launch(intent)
        }
    }


}