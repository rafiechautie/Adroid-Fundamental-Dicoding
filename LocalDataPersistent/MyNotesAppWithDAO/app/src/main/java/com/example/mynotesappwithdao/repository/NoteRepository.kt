package com.example.mynotesappwithdao.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mynotesappwithdao.database.Note
import com.example.mynotesappwithdao.database.NoteDao
import com.example.mynotesappwithdao.database.NoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * kelas ini berfungsi sebagai penghubung antara ViewModel dengan database atau resource data
 */

class NoteRepository(application: Application) {

    /**
     * inisiasi NoteDao
     */
    private val mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    /**
     * menghubungkan NoteDao dan NoteRoomDatabase dengan tujuan supaya dapat menggunakan databasenya
     */
    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    /**
     * Pada kelas ViewModel, Anda bisa mendapatkan list notes dengan cara memanggil metode getAllNotes().
     * Hal ini karena Anda menggunakan LiveData yang bersifat asynchronous.
     */
    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()

    /**
     * berbeda pada bagian insert, update dan delete, aksi tersebut harus dijalankan menggunakan ExecutorService.
     * Jika proses di atas hanya dilakukan tanpa executorService, maka akan terjadi force close.
     * Hal ini disebabkan karena proses insert, update dan delete menggunakan thread yang berbeda yakni background thread.
     */
    fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }

    fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }
}