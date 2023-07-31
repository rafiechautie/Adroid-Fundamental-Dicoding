package com.example.mytestingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mytestingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    /**
     * dibuat sebuah array dengan nama "names"
     */
    private var names = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * trigger ketika button setValue ditekan
         */
        binding.btnSetValue!!.setOnClickListener(this)

        /**
         * menambahkan value ke array berjumlah 3 sehingga dia punya index sebanyak 2
         */
        names.add("Muhammad Rafie Chautie")
        names.add("Izzati Millah Hanifah")
        names.add("Dora Yusanto")


    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_set_value){
            /**
             * ketika button set value ditekan akan mengubah text denga id tvText menjadi 19
             */
//            binding.tvText.text = "19"
            Log.d("MainActivity", names.toString())
            val name = StringBuilder()
            /**
             * memanggil array dari 0 sampe 3
             *
             * karna memanggil sampe 3 sedangkan index array pada variable "names" hanya sampe 2
             * maka terjadi error IndexOutOfBoundsException
             */
            for (i in 0..3){
                name.append(names[i]).append("\n")
            }
            binding.tvText.text = name.toString()
        }
    }
}