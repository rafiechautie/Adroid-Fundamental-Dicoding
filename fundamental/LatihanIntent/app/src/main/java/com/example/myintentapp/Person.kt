package com.example.myintentapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val name: String?,
    val age: String?,
    val email: String?,
    val city: String,
): Parcelable
