package com.example.mylivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.mylivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribe()
    }

    private fun subscribe() {
        /**
         * Jika Anda perhatikan kode di atas, aLong akan selalu diperbarui secara realtime sesuai
         * dengan perubahan yang ada di kelas ViewModel. Namun jika elapsedTimeObserver tidak dipanggil
         * saat melakukan observe getElapsedTime() maka nilai aLong juga tidak akan ada perubahan.
         *
         * Jadi cara mendapatkan value dari sebuah LiveData harus dilakukan dengan cara meng-observe
         * LiveData tersebut. Dan proses ini dilakukan secara asynchronous.
         *
         * val elapsedTimeObserver = Observer<Long?> { aLong -> ... } :
         * membuat sebuah observer untuk menangani perubahan data yang terjadi pada LiveData elapsedTime
         * yang dimiliki oleh sebuah ViewModel. Observer ini akan di-trigger setiap kali terjadi perubahan
         * data pada elapsedTime.
         *
         * { aLong -> ... } : merupakan lambda expression yang akan dijalankan setiap kali terjadi
         * perubahan data pada elapsedTime. aLong merupakan variabel yang berisi data terbaru pada elapsedTime.
         */
        val elapsedTimeObserver = Observer<Long?> { aLong ->
            /**
             * val newText = this@MainActivity.resources.getString(R.string.seconds, aLong) :
             * membuat sebuah string baru yang akan digunakan untuk meng-update tampilan waktu pada
             * aplikasi. String ini dibuat dengan menggunakan sebuah resource string yang didefinisikan
             * pada file XML dengan nama strings.xml. Resource string ini mengandung sebuah placeholder
             * untuk menyimpan nilai aLong yang merupakan data terbaru pada elapsedTime.
             */
            val newText = this@MainActivity.resources.getString(R.string.seconds, aLong)
            binding.timerTextview.text = newText
        }

        /**
         * viewModel.getElapsedTime().observe(this, elapsedTimeObserver) : meng-observe LiveData
         * elapsedTime yang dimiliki oleh ViewModel dengan mem-passing observer elapsedTimeObserver.
         * Dengan cara ini, setiap kali terjadi perubahan data pada elapsedTime, observer elapsedTimeObserver
         * akan di-trigger.
         */
        viewModel.getElapsedTime().observe(this, elapsedTimeObserver)
    }
}
