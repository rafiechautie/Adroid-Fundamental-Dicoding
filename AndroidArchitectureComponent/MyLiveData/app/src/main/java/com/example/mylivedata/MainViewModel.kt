package com.example.mylivedata

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel: ViewModel() {

    private val mInitialTime = SystemClock.elapsedRealtime()

    /**
     * objek liveData yang akan disubscribe oleh MainActivity
     *
     * value pada mutable data bisa diubah sedangkan live data tidak
     */
    private val mElapsedTime = MutableLiveData<Long?>()

    /**
     * metode untuk menjalankan timer
     */
    init {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                /**
                 * yang dimaksud mutable data bisa diubah datanya adalah code dibawah ini
                 *
                 * ubah data / update data secara real time berdasarkan value newValue
                 *
                 * jadi secara realtime MutableLiveData akan menerima data yang baru.
                 * Lalu apa bedanya antara postValue(), getValue() dan setValue().
                 *
                 * setValue() Menetapkan sebuah nilai dari LiveData. Jika ada observer yang aktif,
                 * nilai akan dikirim kepada mereka. Metode ini harus dipanggil dari main thread.
                 *
                 * postValue() Posting tugas ke main thread untuk menetapkan nilai yang diberikan.
                 * Jika Anda memanggil metode ini beberapa kali sebelum main thread menjalankan tugas
                 * yang diposting, hanya nilai terakhir yang akan dikirim.
                 *
                 * getValue()
                 * Mendapatkan nilai dari sebuah LiveData.
                 *
                 * Intinya adalah setValue() bekerja di main thread dan postValue() bekerja di worker thread.
                 */
                mElapsedTime.postValue(newValue)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

    fun getElapsedTime(): LiveData<Long?> {
        return mElapsedTime
    }

    companion object {
        private const val ONE_SECOND = 1000
    }
}