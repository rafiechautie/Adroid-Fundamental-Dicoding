package com.example.myrecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * data class adalah sebuah class yang tidak bergantuk pada kelas lainnya
 *
 * class ini berfungsi untuk menyimpan model data sebuah object
 */
@Parcelize
data class Hero(
    var name: String,
    var desc: String,
    var photo: String
): Parcelable
