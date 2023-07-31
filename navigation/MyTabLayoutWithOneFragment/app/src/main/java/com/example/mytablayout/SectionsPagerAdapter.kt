package com.example.mytablayout

import android.os.Bundle
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

    var appName: String = ""

    /**
     * getItemCount digunakan untuk menentukan jumlah tab yang ingin ditampilkan
     */
    override fun getItemCount(): Int {
        return 3
    }

    /**
     * fungsi createFragment digunakan untuk menampilkan fragment sesuai dgn posisi tabnya
     */
    override fun createFragment(position: Int): Fragment {
        var fragment = HomeFragment()
        /**
         * setArgument digunakan untuk mengirimkan data bundle tersebut ke fragment.
         */
        fragment.arguments = Bundle().apply {
            /**
             * Di dalam fungsi tersebut Anda memasukkan parameter yang dikirimkan ke dalam Bundle
             * sesuai dengan tipe datanya dengan format Key-Value,
             * dengan ARG_SECTION_NUMBER bertindak sebagai key dan position + 1 sebagai value.
             * Mengapa ada +1? hal ini karena position dimulai dari 0, sedangkan Anda ingin menampilkan
             * urutan tab yang dimulai dari 1.
             */
            putInt(HomeFragment.ARG_SECTION_NUMBER, position+1)
            /**
             * mengirimkan data appName ke HomeFragment menggunakan key dan value, key bertindak
             * sebagai ARG_NAME, dan valuenya adalah appName
             */
            putString(HomeFragment.ARG_NAME, appName)
        }
        return fragment
    }
}