package com.example.mynotesapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mynotesapp.databinding.ActivityNoteAddUpdateBinding
import com.example.mynotesapp.db.DatabaseContract
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion.DATE
import com.example.mynotesapp.db.NoteHelper
import com.example.mynotesapp.entity.Note
import java.text.SimpleDateFormat
import java.util.*

/**
 * Tanggung jawab utama NoteAddUpdateActivity adalah sebagai berikut:
 *
 * 1. Menyediakan form untuk melakukan proses input data.
 * 2. Menyediakan form untuk melakukan proses pembaruan data.
 * 3. Jika pengguna berada pada proses pembaruan data maka setiap kolom pada form sudah terisi otomatis
 * dan ikon untuk hapus yang berada pada sudut kanan atas ActionBar ditampilkan dan berfungsi untuk menghapus data.
 * 4. Sebelum proses penghapusan data, dialog konfirmasi akan tampil. Pengguna akan ditanya terkait penghapusan yang akan dilakukan.
 * 5. Jika pengguna menekan tombol back (kembali) baik pada ActionBar maupun peranti, maka akan tampil
 * dialog konfirmasi sebelum menutup halaman.
 * 6. Masih ingat materi di mana sebuah Activity menjalankan Activity lain dan menerima nilai balik
 * pada metode registerForActivityResult()? Tepatnya di Activity yang dijalankan dan ditutup dengan
 * menggunakan parameter RESULTCODE. Jika Anda lupa, baca kembali modul 1 tentang Intent ya!
 */
class NoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNoteAddUpdateBinding

    private var isEdit = false
    private var note: Note? = null
    private var position: Int = 0
    private lateinit var noteHelper: NoteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * membuka koneksi dengan database
         */
        noteHelper = NoteHelper.getInstance(applicationContext)
        noteHelper.open()

        note = intent.getParcelableExtra(EXTRA_NOTE)
        /**
         * ngecek apakah data note yang di click udah diisi atau belom
         */
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle: String
        val btnTitle: String

        /**
         * jika isEdit True maka title dan buttonya diubah menjadi "ubah" dan "update"
         */
        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            note?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
            }
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener(this)
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit){
            /**
             * mengambil data yang diinput oleh user
             */
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()

            /**
             * melakukan validasi
             */
            if (title.isEmpty()) {
                binding.edtTitle.error = "Field can not be blank"
                return
            }

            note?.title = title
            note?.description = description


            val intent = Intent()
            intent.putExtra(EXTRA_NOTE, note)
            intent.putExtra(EXTRA_POSITION, position)

            /**
             * Untuk memasukkan data ke database, kita perlu membungkus data pada ContentValues
             * dan memasukkanya dengan memanggil fungsi insert pada NoteHelper.
             */
            val values = ContentValues()
            values.put(DatabaseContract.NoteColumns.TITLE, title)
            values.put(DatabaseContract.NoteColumns.DESCRIPTION, description)

            if (isEdit) {
                /**
                 * jika dia dalam kondisi edit
                 *
                 * Untuk mengetahui apakah proses edit berhasil atau tidak,
                 * kita dapat mengetahuinya dengan membaca nilai kembalian dari proses tersebut yang bertipe Long
                 *
                 * jika nilai result lebih dari 0, maka proses edit dikatakan berhasil dan kita bisa
                 * setResult ke MainActivity dengan data baru tersebut. Jika gagal, maka akan ditampilkan Toast.
                 */
                val result = noteHelper.update(note?.id.toString(), values).toLong()
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    Toast.makeText(this@NoteAddUpdateActivity, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                }
            } else {
                /**
                 * jika dalam kondiri tambah data
                 */
                note?.date = getCurrentDate()
                values.put(DATE, getCurrentDate())
                val result = noteHelper.insert(values)
                /**
                 * jika nilai result lebih dari 0 maka proses insert berhasil
                 *
                 * Untuk mengetahui apakah proses Insert berhasil atau tidak,
                 * kita dapat mengetahuinya dengan membaca nilai kembalian dari proses tersebut yang bertipe Long
                 *
                 * jika ilai result lebih dari 0, maka proses Insert dikatakan berhasil dan kita bisa
                 * setResult ke MainActivity dengan data baru tersebut. Jika gagal, maka akan ditampilkan Toast.
                 */
                if (result > 0) {
                    note?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(this@NoteAddUpdateActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * fungsi untuk mengambil date
     */
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    /**
     * method untuk memanggil menu_form.xml pada NoteAddUpdateAcitivity
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * method untuk memberikan aksi ketika menu(delete) diclick
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * ketika menekan tombol back maka akan memunculkan alert
     */
    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    /**
     * memnuculkan showAlertDialog
     */
    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Note"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                /**
                 * jika tidak jadi untuk menghapus maka hentikan penampilan aler dialog
                 */
                if (isDialogClose) {
                    finish()
                } else {
                    /**
                     * jika ingin menghapus data
                     */
                    val result = noteHelper.deleteById(note?.id.toString()).toLong()
                    /**
                     * jika proses penghapusan berhasil maka lansung arahkan ke main activity dan kirim "RESULT_DELETE"
                     */
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(this@NoteAddUpdateActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}