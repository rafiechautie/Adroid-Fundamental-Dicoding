package com.example.mydatastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 *  terdapat constructor pada ViewModel. Perlu diketahui, kita tidak bisa membuat ViewModel secara langsung.
 *  Untuk itu, kita akan membuat class yang extend ke ViewModelProvider untuk bisa memasukkan constructor ke dalam ViewModel
 */
class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    /**
     * Perlu diperhatikan juga bahwa nilai kembalian dari fungsi ini berupa Flow. Flow merupakan
     * salah satu bagian dari Coroutine yang digunakan untuk mengambil data secara berkelanjutan (data stream)
     * dengan jumlah yang banyak. Flow sering digunakan untuk membuat reactive programming yang akan dipelajari
     * lebih lanjut pada kelas Expert. Untuk saat ini, karena keluaran dari DataStore masih berupa Flow,
     * maka kita perlu mengubahnya menjadi LiveData pada VIewModel dengan cara seperti berikut:
     *
     * asLiveData merupakan fungsi yang digunakan untuk mengubah Flow menjadi LiveData.
     * Anda dapat melakukan ini karena telah menambahkan library lifecycle-livedata-ktx sebelumnya di awal latihan.
     */
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        /**
         * viewModelScope merupakan scope yang sudah disediakan library lifecycle-viewmodel-ktx
         * untuk menjalankan coroutine pada ViewModel yang sudah aware dengan lifecycle.
         * Dengan begitu instance coroutine akan otomatis dihapus ketika ViewModel dibersihkan sehingga
         * aplikasi tidak mengalami memory leak (kebocoran memori). Selanjutnya di sini kita menggunakan
         * method launch karena kita akan memulai background process tanpa nilai kembalian alias fire and forget
         */
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}