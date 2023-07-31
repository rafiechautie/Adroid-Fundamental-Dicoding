package com.example.myviewmodel

import androidx.lifecycle.ViewModel

/**
 * dengan menambahkan turunan elas ViewModel ke kelas MainViewModel, itu menandakan bahwa kelas tersebut
 * menjadi kelas ViewModel. Segala sesuatu yang ada di kelas tersebut akan terjaga selama
 * Activity masih dalam keadaan aktif
 */
class MainViewModel: ViewModel() {
    var result = 0

    fun calculate(width: String, height: String, length: String){
        /**
         * nilai result akan selalu dipertahankan selama MainViewModel masih terikat dengan Activity.
         */
        result = width.toInt() * height.toInt() * length.toInt()
    }
}