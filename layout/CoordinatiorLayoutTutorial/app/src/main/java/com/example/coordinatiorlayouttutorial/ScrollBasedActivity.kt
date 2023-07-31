package com.example.coordinatiorlayouttutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coordinatiorlayouttutorial.databinding.ActivityLayoutBasedBinding
import com.example.coordinatiorlayouttutorial.databinding.ActivityScrollBasedBinding
import com.google.android.material.appbar.CollapsingToolbarLayout

class ScrollBasedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollBasedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollBasedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar_layout = title
        // You can add the code for your FAB also.
    }
}