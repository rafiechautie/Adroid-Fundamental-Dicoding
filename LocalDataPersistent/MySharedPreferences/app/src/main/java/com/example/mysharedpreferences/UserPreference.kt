package com.example.mysharedpreferences

import android.content.Context

/**
 * kelas UserPreference digunakan untuk memanipulasi object sharedPreferences yang dibuat
 */
internal class UserPreference(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Ketika Anda membuat obyek dari kelas UserPreference pada Activity berikutnya,
     * maka obyek Shared Preferences akan diciptakan dan hanya diciptakan sekali.
     * Jika sudah ada, obyek yang sudah ada yang akan dikembalikan. Semua itu Anda lakukan di konstruktor kelas UserPreference.
     */

    /**
     * untuk mengisi data ke sharesPrefered
     */
    fun setUser(value: UserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putInt(AGE, value.age)
        editor.putString(PHONE_NUMBER, value.phoneNumber)
        editor.putBoolean(LOVE_MU, value.isLove)

        editor.apply()
    }

    /**
     * untuk mengambil data user
     *
     * perhatikan bahwa parameter kedua adalah nilai default jika data user belum ada
     *
     * contohnya yaitu ketika aplikasi pertama kali dijalankan maka semua preferences pasti belum
     * ada datanya. Begitu juga untuk tipe boolean, dimana nilai false menjadi nilai default ketika
     * Anda mengakses method isLoveMU() untuk nilai yang tersimpan pada key KEY_LOVE_MU.
     */
    fun getUser(): UserModel {
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.email = preferences.getString(EMAIL, "")
        model.age = preferences.getInt(AGE, 0)
        model.phoneNumber = preferences.getString(PHONE_NUMBER, "")
        model.isLove = preferences.getBoolean(LOVE_MU, false)

        return model
    }

    /**
     * kode dibawah adalah sekumpulan variable KEY, nama preference, dan obyek preference.
     * Anda juga bisa memindahkan nilai dari semua Key dan nama preference ke dalam file strings.xml
     * seperti pada cara sebelumnya. Untuk memudahkan Anda, tuangkan semua ke dalam satu kelas.
     */
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val LOVE_MU = "islove"
    }

}
