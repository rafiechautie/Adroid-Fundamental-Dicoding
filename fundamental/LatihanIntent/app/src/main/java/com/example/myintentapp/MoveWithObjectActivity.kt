package com.example.myintentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myintentapp.databinding.ActivityMoveWithObjectBinding

class MoveWithObjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoveWithObjectBinding

    //static variable
    companion object{
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveWithObjectBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /**
         * menerima dan mengambil data yang dikirim dari MainActivity yang berupa object
         */
        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as Person

        /**
         * menaruk data ke dalam sebuah view
         */
        val text = "Name : ${person.name.toString()},\nEmail : ${person.email},\nAge : ${person.age},\nLocation : ${person.city}"
        binding.tvObjectReceived.text = text

    }
}