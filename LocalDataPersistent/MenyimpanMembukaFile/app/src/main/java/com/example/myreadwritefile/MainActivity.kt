package com.example.myreadwritefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNew.setOnClickListener(this)
        binding.buttonOpen.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    /**
     * Proses new/clear file di sini sangat sederhana, yaitu dengan mengosongkan title, konten, lalu menampilkan toast.
     */
    private fun newFile() {
        binding.editTitle.setText("")
        binding.editFile.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        /**
         * Dengan menggunakan fileList(), Anda akan memperoleh semua nama berkas yang ada pada App Storage aplikasi.
         * Dengan memanfaatkan AlertDialog pada beberapa baris di atas, Anda dapat membuat daftar pilihan berkas sederhana.
         * Setelah salah satu berkas pada daftar tersebut di pilih, maka aplikasi akan memanggil metode loadData().
         */
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) { dialog, item -> loadData(items[item].toString()) }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        /**
         * Dengan menggunakan method static pada kelas FileHelper,
         * Anda dapat mengambil isi dari file yang dipilih dan memasukkannya ke dalam FileModel.
         */
        val fileModel = FileHelper.readFromFile(this, title)
        binding.editTitle.setText(fileModel.filename)
        binding.editFile.setText(fileModel.data)
        Toast.makeText(this, "Loading " + fileModel.filename + " data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when {
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            binding.editFile.text.toString().isEmpty() -> Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()

                /**
                 * Kita melakukan pemeriksaan apakah judul kosong karena membutuhkan nama ketika hendak menyimpan data.
                 * Dengan menyediakan nama file, konten, dan context, Anda dapat langsung menyimpan data dengan memanfaatkan FileHelper Anda.
                 */
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.filename + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}