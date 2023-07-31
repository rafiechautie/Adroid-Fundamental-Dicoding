package com.example.barvolume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.barvolume.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    /**
     * untuk menjaga data ketika aplikasi di create kembali(di rotate)
     *
     * companion object sering digunakan untuk membuat komponen yang
     * static dan bisa diakses di luar class
     */
    companion object{
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnResult.setOnClickListener(this)

        /**
         * untuk menjaga data ketika aplikasi di create ulang (di rotate)
         *
         * sistemnya ialah value yang ada di key STATE_RESULT akan dimasukkan ke variable result
         *
         * setelah itu value tersebut akan kita ambil dan kita tampilkan ke view tvResult
         * yang telah disimpan di bundle
         */
        if (savedInstanceState != null){
            val result  = savedInstanceState.getString(STATE_RESULT)
            binding.tvResult.text = result
        }
    }

    /**
     * untuk menjaga data ketika aplikasi di create ulang (di rotate)
     *
     * fungsi onSaveIntanceState akan dipanggil secara otomatis sebelum
     * sebuah activity dihancurkan
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        /**
         * hasil yang ada pada tvResult dimasukkan ke dalam bundle
         * kemudian disimpan isinya
         *
         * penyimpanan disini menggunakan konsep key-value dimana key
         * adalah STATE_RESULT dan isi dari tvResult adalah value
         *
         *
         */
        outState.putString(STATE_RESULT, binding.tvResult.text.toString())
    }

    /**
     * jika ada button yang diclick maka function onClick akan dipanggil
     */
    override fun onClick(v: View) {
        if (v.id == R.id.btn_result){
            val inputLength = binding.edtLength.text.toString().trim()
            val inputWidth = binding.edtWidth.text.toString().trim()
            val inputHeight = binding.edtHeight.text.toString().trim()


            /**
             * melakukan validasi jika input text tidak diisi user
             */
            var isEmptyFields = false

            if (inputLength.isEmpty()) {
                isEmptyFields = true
                binding.edtLength.error = "Field ini tidak boleh kosong"
            }
            if (inputWidth.isEmpty()) {
                isEmptyFields = true
                binding.edtWidth.error = "Field ini tidak boleh kosong"
            }
            if (inputHeight.isEmpty()) {
                isEmptyFields = true
                binding.edtHeight.error = "Field ini tidak boleh kosong"
            }


            /**
            * rumus volume balok -> panjang * lebar * tinggi
             *
             * default saat input text itu bertipe data string, sehingga harus dikonversi menjadi
             * tipe data double
             */
            if(!isEmptyFields){
                val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                /**
                 * untuk menampilkan menggunakan textview maka variabel tersebut harus bertipe data string
                 */
                binding.tvResult.text = volume.toString()
            }
        }
    }
}