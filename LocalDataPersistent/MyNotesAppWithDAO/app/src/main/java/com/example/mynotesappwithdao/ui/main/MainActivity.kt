package com.example.mynotesappwithdao.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotesappwithdao.R
import com.example.mynotesappwithdao.databinding.ActivityMainBinding
import com.example.mynotesappwithdao.helper.ViewModelFactory
import com.example.mynotesappwithdao.ui.insert.NoteAddUpdateActivity

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        /**
         * menghubungkan mainviewmodel dengan mainactivity
         */
        val mainViewModel = obtainViewModel(this@MainActivity)
        /**
         * Kode di bawah berfungsi untuk meng-observe data dari getAllNotes().
         * Setelah ada perubahan data, maka akan tampil di RecyclerView.
         */
        mainViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        adapter = NoteAdapter()

        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter

        binding?.fabAdd?.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        /**
         * Mengapa kita perlu menggunakan ViewModelFactory? Hal ini karena kita perlu mengirim context
         * ke dalam ViewModel yang nantinya digunakan untuk inisialisasi database di dalam NoteRepository.
         * Penggunaan factory ini juga bisa digunakan untuk mengirim parameter lainnya
         */
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}