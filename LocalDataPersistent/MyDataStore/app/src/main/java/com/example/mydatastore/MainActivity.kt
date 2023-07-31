package com.example.mydatastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mydatastore.databinding.ActivityMainBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Pada kode di atas, kita membuat extension properties pada Context dengan nama dataStore yang
     * dibuat dengan menggunakan property delegation by preferencesDataStore. Property delegation
     * adalah sebuah mekanisme untuk mendelegasikan suatu tugas kepada class lain. Dengan menggunakan
     * cara ini, Anda tidak perlu tahu bagaimana cara membuat DataStore secara detail,
     * Anda cukup menyerahkannya saja ke class preferencesDataStore. Selain itu, kode ini dibuat
     * di top-level supaya menjadi Singleton yang cukup dipanggil sekali.
     */
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * mengambil layout switch
         */
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        /**
         *memanggil instance data store
         */
        val pref = SettingPreferences.getInstance(dataStore)

        /**
         * inisialisasi mainviewmodel agar bisa berinteraksi dengan data store
         */
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        /**
         * mengambil data
         */
        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            })

        /**
         * mendeteksi aksi switch
         *
         * ketika user menekan switch maka datanya akan lansung disimpan ke data store
         */
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}