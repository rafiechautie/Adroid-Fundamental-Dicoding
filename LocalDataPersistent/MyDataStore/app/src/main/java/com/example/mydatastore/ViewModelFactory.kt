package com.example.mydatastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    /**
     * Dengan ViewModelFactory, Anda dapat memasukkan constructor dengan cara mengirim data ke VIewModelFactory
     * terlebih dahulu, baru setelah itu dikirimkan ke ViewModel pada fungsi create.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}