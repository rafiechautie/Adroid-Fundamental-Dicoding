package com.example.myintentapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myintentapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    /**
     * kode dibawah akan dijalankan ketika user telah mengirimkan data dari
     * MoveForActivityResult ke MainActivity
     *
     * untuk mendapatkan nilai kembalian membutuhkan registerForActivityResult
     */
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        /**
         * dilakukan pengecekan antara resultCode sama value yang dikirim oleh MoveForResultAcitivity dan juga diperiksan apakah data
         * yang dikembalikan bernilai null atau tidak
         */
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null){
            val selectedValue = result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            binding.tvResult.text = "Hasil : $selectedValue"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMoveActivity.setOnClickListener(this)
        binding.btnMoveActivityWithData.setOnClickListener(this)
        binding.btnMoveActivityWithObject.setOnClickListener(this)
        binding.btnImplicitIntent.setOnClickListener(this)
        binding.btnMoveActivityWithResult.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            /**
             * pindah activity tanpa membawa data
             */
            R.id.btn_move_activity -> {
                val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
                startActivity(moveIntent)
            }
            /**
             * pindah activity dengan membawa data
             */
            R.id.btn_move_activity_with_data -> {
                val moveWithDataIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                /**
                 * method putExtra digunakan untuk mengirimkan data bersamaan dengan objek Intent
                 *
                 * method putExtra merupakan metode menampung data dengan key-value
                 */
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Muhammad Rafie Chautie")
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 21)
                startActivity(moveWithDataIntent)
            }
            /**
             * pindah activity dengan membawah sebuah data yang kompleks (object)
             */
            R.id.btn_move_Activity_with_object -> {
                //isi data person
                val person = Person(
                    "Muhammad Rafie Chautie",
                    "21",
                    "rafiqauti13@gmail.com",
                    "Jambi"
                )
                val moveWithObjectIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)
                startActivity(moveWithObjectIntent)
            }
            /**
             * contoh penggunaan implicit intent dengan pindah ke activity dial number
             */
            R.id.btn_implicit_intent -> {
                val phoneNumber = "081210841382"
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialPhoneIntent)
            }
            R.id.btn_move_activity_with_result -> {
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }
        }
    }

}