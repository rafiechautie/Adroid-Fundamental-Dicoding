package com.example.mysharedpreferences

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * class yang digunakan untuk menyimpan data yang diinput user
 *
 * dengan Parcelable maka sebuah object dapat diubah menjadi bentuk parcel dan dapat dikirim
 * datanya melalui intent atau bundle
 */
@Parcelize
data class UserModel(
    var name: String? = null,
    var email: String? = null,
    var age: Int = 0,
    var phoneNumber: String? = null,
    var isLove: Boolean = false
): Parcelable
