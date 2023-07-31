package com.example.mynotesapp.db

import android.database.Cursor
import com.example.mynotesapp.entity.Note

/**
 * kelas pembantu untuk mengoversi dari cursor ke arraylist
 *
 * Pada NoteHelper proses load data dilakukan dengan eksekusi queryAll() menghasilkan objek Cursor,
 * namun pada adapter kita membutuhkan dalam bentuk ArrayList, maka dari itu kita harus mengonversi
 * dari Cursor ke Arraylist, di sinilah fungsi kelas pembantu MappingHelper.  moveToNext digunakan
 * untuk memindahkan cursor ke baris selanjutnya. Di sini kita ambil datanya satu per satu menggunakan
 * getColumnIndexOrThrow dan dimasukkan ke dalam ArrayList.
 */
object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Note> {
        val notesList = ArrayList<Note>()

        /**
         * Fungsi apply digunakan untuk menyederhanakan kode yang berulang. Misalnya notesCursor.geInt
         * cukup ditulis getInt dan notesCursor.getColumnIndexOrThrow cukup ditulis getColumnIndexOrThrow.
         */
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
                notesList.add(Note(id, title, description, date))
            }
        }
        return notesList
    }

}