package com.example.mynotesappwithdao.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * . Kelas ini nantinya digunakan untuk melakukan eksekusi quiring
 */

@Dao
interface NoteDao {

    /**
     * Kode di bawah digunakan untuk melakukan aksi CRUD(Create, Read, Update dan Delete).
     */

    /**
     * Menggunakan @Insert untuk query insert pada database sesuai dengan input entitas yang dimasukkan,
     * contohnya jika pada perintah di atas adalah Note.
     * Sedangkan kode OnConflictStrategy.IGNORE digunakan jika data yang dimasukkan sama, maka dibiarkan saja.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    /**
     * Menggunakan @Update untuk query update item pada database, contohnya adalah mengupdate sebuah item Note.
     */
    @Update
    fun update(note: Note)

    /**
     * Menggunakan @Delete untuk menghapus sebuah item tertentu, contohnya pada baris tersebut akan
     * menghapus sebuah item note dengan cara mencocokkan item mana yang sama.
     */
    @Delete
    fun delete(note: Note)

    /**
     * Selain itu, Anda juga bisa melakukan query atau menjalankan intruksi atau perintah untuk
     * mengeksekusi sebuah aksi dengan anotasi @Query. Contohnya kode di atas berfungsi untuk
     * mendapatkan semua note dengan pengurutan berdasarkan id terkecil ke besar.
     */
    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

}