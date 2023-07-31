package com.example.mydatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>)  {

    /**
     * Untuk menyimpan data, yang Anda perlukan hanyalah instance DataStore dan Key pada SettingPreferences.
     *
     * Perlu diketahui, Key ini harus unik supaya tidak tercampur dengan data lain. Key ini juga akan
     * diperlukan untuk mengambil data yang sama. Selain itu, key juga harus sesuai dengan tipe data
     * yang akan disimpan. Sebagai contoh, jika ingin menyimpan pengaturan tema yang berupa True/False,
     * gunakanlah Boolean. Apabila Anda ingin menyimpan data lain dengan tipe data yang berbeda,
     * Anda harus menyesuaikan juga.
     */
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    /**
     * function getThemeSetting adalah untuk membaca data. yang diperlukan hanyalah instance datastore dan
     * key pada setting preference
     *
     * Untuk mengambil data yang sudah disimpan, kita menggunakan fungsi map pada variabel data.
     * Pastikan Anda menggunakan key yang sama dengan saat Anda menyimpannya untuk mendapatkan data
     * yang tepat. Selain itu, Anda juga dapat menambahkan elvis operator untuk memberikan nilai default
     * jika datanya masih kosong/null.
     */
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            /**
             * kalau data di datastorenya kosong, maka kasih default false
             */
            preferences[THEME_KEY] ?: false
        }
    }


    /**
     * fungsi saveThemeSetting digunakan untuk menyimpan data
     *
     * , kita menggunakan fungsi lambda edit dengan parameter preferences yang merupakan MutablePrefereces.
     * Untuk mengubah data, Anda perlu menentukan key data yang ingin diubah dan isi datanya. Selain itu,
     * karena edit adalah suspend function, maka ia harus dijalankan di coroutine atau suspend function juga.
     */
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    /**
     * Perlu diketahui bahwa instance DataStore harus berupa Singleton. Singleton hanya memperbolehkan
     * ada satu instance yang digunakan di banyak tempat. Apabila terdapat 2 instance yang aktif dibuat,
     * akan muncul eror sebagai berikut:
     *
     * java.lang.IllegalStateException: There are multiple DataStores active for the same file....
     *
     * Karena itu untuk membuat SettingPreference, kita tidak menggunakan constructor secara langsung,
     * melainkan melalui fungsi getInstance yang berfungsi sebagai Singleton seperti berikut:
     *
     * Fungsi dari Singleton yaitu dapat menciptakan satu instance saja di dalam JVM,
     * sehingga tidak memakan memori yang terbatas. Jadi, ketika Activity A memanggil SettingPreferences,
     * kelas itu akan membuat instance dalam bentuk volatile. Volatile adalah keyword yang digunakan
     * supaya nilai pada suatu variabel tidak dimasukkan ke dalam cache. Kemudian jika Activity B memanggil
     * fungsi ini, kelas tersebut akan memeriksa apakah instance-nya sudah ada. Jika tidak null,
     * sistem akan mengembalikan instance tersebut pada Activity B, tidak membuat instance baru.
     */
    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}