package com.example.restaurantreview

/**
 * Seperti yang sudah Anda pahami di bahasa Kotlin, T adalah tipe generic yang bisa digunakan
 * kelas ini dapat membungkus berbagai macam data. Data yang dibungkus tersebut
 * kemudian akan dimasukkan ke dalam variabel content.
 */
open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    /**
     * fungsi utama dari kelas ini yaitu terdapat pada fungsi getContentIfNotHandled(). Fungsi
     * tersebut akan memeriksa apakah aksi ini pernah dieksekusi sebelumnya.
     * Caranya yaitu dengan memanipulasi variabel hasBeenHandled.
     *
     * Awalnya variabel hasBeenHandled bernilai false. Kemudian ketika aksi pertama kali dilakukan
     * nilai hasBeenHandled akan diubah menjadi true. Sedangkan pada aksi selanjutnya
     * ia akan mengembalikan null karena hasBeenHandled telah bernilai true.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     *  fungsi peekContent yang bisa Anda gunakan untuk melihat nilai dari content walaupun aksi event sudah dilakukan.
     */
    fun peekContent(): T = content


}