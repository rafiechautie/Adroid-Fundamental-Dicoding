package com.example.myreadwritefile

import android.content.Context

internal object FileHelper {

    /**
     * Pada kedua contoh di bawah, metode writeToFile dan readFromFile berbentuk static.
     * Karena sifatnya yang static, maka kedua metode tersebut dapat dipanggil tanpa menginisiasi kelas yang memilikinya.
     */

    /**
     * fungsi untuk menyimpan inputan dari user
     *
     * Logika sederhana yang dilakukan oleh metode di atas adalah menyimpan data yang bertipekan string
     * ke dalam sebuah berkas pada internal storage. Dengan menggunakan komponen FileOutputStream,
     * Anda dapat menulis data ke dalam berkas menggunakan stream.
     */
    fun writeToFile(fileModel: FileModel, context: Context) {
        /**
         * Pada proses inisiasi FileOutputStream, Anda menggunakan metode openFileOutput()
         * untuk membuka berkas sesuai dengan namanya. Jika berkas belum ada,
         * maka berkas tersebut akan secara otomatis dibuatkan. Untuk menggunakan method openFileOutput()
         * Anda harus mengetahui context aplikasi yang menggunakannya. Oleh karena itu,
         * dalam metode ini Anda memberikan inputan parameter context. Setelah berkas dibuka,
         * Anda dapat menulis data menggunakan metode write(data).
         */
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    /**
     * fungsi untuk membaca inputan dari user
     */
    fun readFromFile(context: Context, filename: String): FileModel {
        val fileModel = FileModel()
        fileModel.filename = filename
        /**
         * Pada metode readFromFile(), kita menggunakan komponen FileInputStream dengan metode
         * openFileInput. Data pada berkas akan dibaca menggunakan stream dan data pada tiap baris
         * dalam berkas akan mampu diperoleh dengan menggunakan bufferedReader.
         */
        fileModel.data = context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        return fileModel
    }
}