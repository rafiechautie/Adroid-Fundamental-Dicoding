package com.example.mytablayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Untuk mengatur ViewPager2 dan komponen yang ada di dalamnya,
 * Anda memerlukan SectionsPagerAdapter yang extend ke kelas FragmentStateAdapter.
 *
 * FragmentStateAdapter merupakan base adapter yang digunakan untuk mengatur data pada ViewPager2.
 * Dengan extends ke abstract class ini, Anda diminta untuk mengimplementasikan 2 fungsi utama yaitu,
 * createFragment dan getItemCount.
 *
 * juga terdapat constructor yang diperlukan yaitu AppCompatActivity karena kita menggunakan Activity.
 * Apabila Anda menerapkannya di Fragment, gunakanFragmentActivity.
 */
class SectionsPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    /**
     * getItemCount digunakan untuk menentukan jumlah tab yang ingin ditampilkan
     */
    override fun getItemCount(): Int {
        return 2
    }

    /**
     * fungsi createFragment digunakan untuk menampilkan fragment sesuai dgn posisi tabnya
     */
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = ProfileFragment()
        }
        return fragment as Fragment
    }
}