package com.example.mytablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.example.mytablayout.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val viewPager: ViewPager2 = binding.viewPager
        /**
         * menghubungkan sectionsPagerAdapter dengan viewpager2
         */
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter

        /**
         * memberikan nilai pada variable appName
         */
        sectionsPagerAdapter.appName = resources.getString(R.string.app_name)


        val tabs: TabLayout = binding.tabs

        /**
         * menghubungkan viewpager2 dengan tab layout
         *
         * Dengan menerapkan TabLayoutMediator,
         * maka Fragment yang tampil pada ViewPager2 akan sesuai dengan posisi yang dipilih pada tab
         */
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            /**
             * TAB_TITLES digunakan untuk enentukan judul dari masing-masing Tab yang diambil
             * sesuai dengan urutan porsinya
             */
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f




    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
        )
    }
}