package com.example.myflexiblefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myflexiblefragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * menanamkan homefragment ke dalam MainActivity
         *
         * untuk menempelkan fragment home pada activity kita menggunakan supportFragmentManager
         */
        val mFragmentManager = supportFragmentManager
        val mHomeFragment = HomeFragment()
        val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment){
            Log.d(TAG, "Fragment Name: " + HomeFragment::class.java.simpleName)
            mFragmentManager
                //memulai proses perubahan
                .beginTransaction()
                 //menambahkan object fragment ke dalam layout container
                .add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
                 //melakukan pemasangan object secara asynchronous untuk ditampilkan ke antar muka UI
                .commit()
        }
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}