package com.example.mysettingpreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mysettingpreference.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Kode supportFragmentManager.beginTransaction().add(R.id.setting_holder, MyPreferenceFragment()).commit()
         * digunakan untuk menambahkan sebuah Fragment ke dalam layout XML dari sebuah Activity.
         *
         * Pada kode tersebut, supportFragmentManager.beginTransaction() mengembalikan sebuah FragmentTransaction yang
         * digunakan untuk mengelola fragment dalam sebuah activity. Selanjutnya, fungsi add() digunakan untuk menambahkan
         * fragment ke dalam container view yang ditentukan dalam parameter pertama, yaitu R.id.setting_holder,
         * yang mewakili sebuah ViewGroup.
         *
         * Parameter kedua dari fungsi add() adalah objek Fragment yang ingin ditambahkan, yaitu MyPreferenceFragment().
         * Kemudian, commit() dipanggil untuk menyelesaikan transaksi dan menjalankan operasi
         * pengelolaan fragment secara asynchronous.
         *
         * Dengan menambahkan fragment ke dalam container view yang ditentukan, kita dapat menampilkan konten
         * yang berbeda-beda di dalam satu activity. Fragment juga dapat digunakan untuk membuat interaksi
         * antara beberapa tampilan UI dalam satu activity. Contohnya, kita dapat menampilkan daftar
         * item pada satu fragment dan menampilkan detail item pada fragment lain ketika item tersebut di-klik pada daftar item.
         */
        supportFragmentManager.beginTransaction().add(R.id.setting_holder, MyPreferenceFragment()).commit()
    }
}