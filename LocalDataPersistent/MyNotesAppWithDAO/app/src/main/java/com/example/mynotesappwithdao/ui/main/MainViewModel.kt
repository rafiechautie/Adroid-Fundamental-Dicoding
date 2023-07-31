package com.example.mynotesappwithdao.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mynotesappwithdao.database.Note
import com.example.mynotesappwithdao.repository.NoteRepository

/**
 * kelas viewmodel digunakan sebagai penghubung antara Activity dengan Repository
 */

class MainViewModel(application: Application) : ViewModel() {

    /**
     * Bagian kelas ViewModel jadi lebih singkat, hanya menginisialisasi kelas Repository dan mengambil
     * fungsi yang ada pada kelas tersebut.
     */
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    /**
     * Dengan memanggil getAllNotes(), Activity dengan mudah meng-observe data list notes dan bisa segera ditampilkan.
     */
    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()

}