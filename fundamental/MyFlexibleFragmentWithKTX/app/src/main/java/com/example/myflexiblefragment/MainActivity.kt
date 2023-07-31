package com.example.myflexiblefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import com.example.myflexiblefragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * menanamkan homefragment ke dalam MainActivity
         */
        val mFragmentManager = supportFragmentManager
        val mHomeFragment = HomeFragment()
        val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment){
            Log.d(TAG, "Fragment Name: " + HomeFragment::class.java.simpleName)
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
            }

        }
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}