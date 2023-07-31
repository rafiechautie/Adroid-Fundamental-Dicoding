package com.example.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotesapp.databinding.ActivityMainBinding
import com.example.mynotesapp.db.MappingHelper
import com.example.mynotesapp.db.NoteHelper
import com.example.mynotesapp.entity.Note
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Tugas utama MainActivity ada dua. Pertama, menampilkan data dari database pada tabel Note secara ascending.
 *
 * Kedua, menerima nilai balik dari setiap aksi dan proses yang dilakukan di NoteAddUpdateActivity.

 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter


    /**
     * mendapatkan nilai result dari NoteAddUpdateActivity
     */
    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        /**
         * jika ada data yang tersimpan
         */
        if (result.data != null) {

            when (result.resultCode) {
                /**
                 * Akan dipanggil jika request codenya ADD
                 */
                NoteAddUpdateActivity.RESULT_ADD -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    /**
                     * Fungsi addItem pada NoteAdapter berfungsi untuk menambahkan data baru ke dalam
                     * List dan memberi animasi jika ada data baru dengan menggunakan fungsi notifyItemInserted.
                     */
                    adapter.addItem(note)
                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                NoteAddUpdateActivity.RESULT_UPDATE -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    val position = result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    /**
                     * Fungsi updateItem pada NoteAdapter berfungsi untuk mengganti data sesuai posisi
                     * yang ada pada List dan memberi animasi jika ada data baru dengan menggunakan fungsi notifyItemChanged.
                     */
                    adapter.updateItem(position, note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
                NoteAddUpdateActivity.RESULT_DELETE -> {
                    val position = result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    /**
                     * Fungsi removeItem pada NoteAdapter berfungsi untuk menghapus data sesuai posisi
                     * yang ada pada List dan memberi animasi jika ada data yang dihapus dengan menggunakan fungsi notifyItemRemoved.
                     */
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * mengubah judul di actionBar menjadi notes
         */
        supportActionBar?.title = "Notes"

        /**
         * LinearLayoutManager(this) digunakan untuk menentukan tampilan dari RecyclerView dengan menentukan
         * jenis layout manager yang digunakan. Dalam hal ini, LinearLayoutManager digunakan untuk menampilkan
         * data dalam bentuk daftar atau list yang terorganisir secara vertikal atau horizontal.
         */
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        /**
         * Selain itu, binding.rvNotes.setHasFixedSize(true) digunakan untuk meningkatkan performa
         * RecyclerView dengan mengoptimalkan ukuran dan tata letak item-item di dalamnya.
         * Dengan menetapkan nilai true, RecyclerView akan mendapatkan performa yang lebih baik
         * karena dapat melakukan optimisasi pada perhitungan ukuran tampilan item saat scrolling
         */
        binding.rvNotes.setHasFixedSize(true)

        /**
         * untuk memberikan aksi ketika salah satu item diclick (diedit)
         */
        adapter = NoteAdapter(object : NoteAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedNote: Note?, position: Int?) {
                /**
                 * ketika salah satu card view di click maka akan diarahkan ke NoteAddUpdateActivity
                 *
                 * Karena kita mengirimkan data, maka status pada NoteAddUpdateActivity adalah update.
                 * isEdit akan diubah menjadi True, sehingga tampilan pada action bar berjudul "Ubah"
                 * dan pada tombol berisi teks "Update". Ketika pengguna berada pada proses pembaruan data,
                 * setiap kolom pada form sudah terisi otomatis dan terdapat ikon hapus di Action Bar.
                 */
                val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, selectedNote)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
                /**
                 * resultLauncher.launch(intent) adalah kode yang digunakan untuk memulai suatu Activity atau Intent
                 * dan mendapatkan result dari Activity atau Intent tersebut.
                 */
                resultLauncher.launch(intent)
            }
        })

        binding.rvNotes.adapter = adapter

        /**
         * memberikan aksi jika salah satu fab diclick
         */
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
            //mengggunkana lauch supaya mengembalikan nilai
            resultLauncher.launch(intent)
        }


        /**
         * Kode tersebut merupakan bagian dari metode untuk mengembalikan data yang hilang pada saat
         * konfigurasi perangkat berubah seperti saat rotasi layar pada aplikasi Android.
         * Pada awalnya, jika savedInstanceState belum diinisialisasi (null), maka akan dilakukan
         * proses pengambilan data (loadNotesAsync()) secara asynchronous.
         * Namun, jika savedInstanceState sudah diinisialisasi (tidak null), maka akan dilakukan pengambilan
         * data yang tersimpan pada savedInstanceState dengan menggunakan getParcelableArrayList()
         * dengan key EXTRA_STATE. Data yang berhasil diambil akan disimpan pada adapter dengan memanggil
         * method adapter.listNotes. Hal ini bertujuan agar data yang sebelumnya telah diambil tidak hilang
         * saat terjadi perubahan konfigurasi pada perangkat.
         */
        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    /**
     * Fungsi ini digunakan untuk load data dari tabel dan dan kemudian menampilkannya ke dalam list
     * secara asynchronous dengan menggunakan Background process
     *
     * lifecycleScope merupakan scope yang dibuat khusus untuk Android agar dapat menjalankan Coroutine
     * yang aware dengan state pada lifecycle. Anda dapat menggunakan metode ini karena telah menambahkan
     * dependency lifecycle-runtime-ktx di awal latihan. Di sini kita juga menggunakan fungsi async
     * karena kita menginginkan nilai kembalian dari fungsi yang kita panggil. Untuk mendapatkan nilai kembaliannya,
     * kita menggunakan fungsi await().
     */
    private fun loadNotesAsync() {
        lifecycleScope.launch {
            binding.progressbar.visibility = View.VISIBLE
            /**
             * Untuk menggunakan database, hal pertama yang harus Anda lakukan adalah membuka koneksi
             * database terlebih dahulu dengan memanggil fungsi open dari NoteHelper. Kemudian Anda
             * bisa melakukan query seperti mengambil semua data dengan memanggil fungsi queryAll.
             * Terakhir, jika proses sudah selesai, jangan lupa untuk menutup koneksi menggunakan fungsi close.
             */
            val noteHelper = NoteHelper.getInstance(applicationContext)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            /**
             * di cek apakah ada datanya atau tidak
             */
            if (notes.size > 0) {
                adapter.listNotes = notes
            } else {
                adapter.listNotes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
            noteHelper.close()
        }
    }

    /**
     * fungsi untuk memunculkan snackbar
     */
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}