package com.example.coordinatiorlayouttutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coordinatiorlayouttutorial.databinding.ActivityMainBinding
import com.google.android.material.appbar.CollapsingToolbarLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLayoutBased.setOnClickListener {
            startActivity(Intent(this, LayoutBasedActivity::class.java))
        }

        binding.btnScrollBased.setOnClickListener {
            startActivity(Intent(this, ScrollBasedActivity::class.java))
        }
    }
}