package com.example.coordinatiorlayouttutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coordinatiorlayouttutorial.databinding.ActivityLayoutBasedBinding
import com.google.android.material.snackbar.Snackbar

class LayoutBasedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutBasedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBasedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.msg_clicked), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}