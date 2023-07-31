package com.example.mynotesappwithdao.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mynotesappwithdao.database.Note
import com.example.mynotesappwithdao.repository.NoteRepository


/**
 * kelas viewmodel digunakan sebagai penghubung antara Activity dengan Repository
 */
class NoteAddUpdateViewModel(application: Application): ViewModel() {

    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}