package com.example.myintentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myintentapp.databinding.ActivityMoveWithDataBinding

class MoveWithDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoveWithDataBinding

    //static variable
    companion object{
        const val EXTRA_AGE = "extra_age"
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveWithDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * menerima dan memasukkan data yang dikirim dari mainactivity ke variable
         */
        val name = intent.getStringExtra(EXTRA_NAME)
        val age = intent.getIntExtra(EXTRA_AGE, 0)

        /**
         * menampilkan data ke dalam view
         */
        val text = "Name : $name, Your age : $age"
        binding.tvDataReceived.text = text


    }
}