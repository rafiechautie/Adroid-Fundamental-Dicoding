package com.example.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion._ID

/**
 * class NoteHelper digunakan untuk proses manipulasi data yang berada di dalam tabel seperti query
 * untuk pembacaan data yang diurutkan secara ascending, penyediaan fungsi pencarian catatan berdasarkan judul,
 * pembaruan catatan, dan penghapusan catatan.
 */
class NoteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        /**
         * metode untuk menginisiasi database
         */
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
    }

    /**
     * metode untuk membuka koneksi database
     */
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    /**
     * metode untuk menutup koneksi database
     */
    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    /**
     * kode dibawah untuk membuat fungsi CRUD pada sql lite
     */

    /**
     * metode untuk mengambil data
     */
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    /**
     * metode untuk mengambil data dengan id tertentu
     */
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    /**
     * metode untuk menyimpan data
     */
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }


    /**
     * metode untuk memperbarui data
     */
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    /**
     * metode untuk menghapus data
     */
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

}