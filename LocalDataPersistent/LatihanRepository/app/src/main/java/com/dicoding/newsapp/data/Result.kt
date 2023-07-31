package com.dicoding.newsapp.data

/**
 * kelas untuk menyimpan status pengambilan data pada package data dengan nama Result
 */
sealed class Result<out R> private constructor(){
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
