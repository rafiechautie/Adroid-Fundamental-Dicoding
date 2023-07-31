package com.example.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        /**
         * membuat executor supaya proses dapat berjalan secara asyncronous
         *
         * Di sini Anda menggunakan newSingleThreadExecutor yang artinya hanya satu thread yang Anda
         * buat. Sehingga ketika Anda klik tombol berkali-kali hanya satu proses yang dijalankan
         * dan proses lainnya akan dieksekusi setelahnya proses sebelumnya selesai.
         * Jika Anda ingin lebih dari satu thread dan melihat perbedaannya,
         * Anda bisa menggunakan newFixedThreadPool atau newCachedThreadPool.
         */
        val executor = Executors.newSingleThreadExecutor()

        /**
         * membuat handler supaya bisa berkomunikasi dengan ui thread saat berada di thread lain
         *
         * dengan menggunakan getMainLooper maka proses di dalam handler dijalankan oleh main/ui thread
         */
        val handler = Handler(Looper.getMainLooper())

        btnStart.setOnClickListener {
//            executor.execute{
//                try {
//                    //simulate proccess compressing
//                    for (i in 0..10) {
//                        Thread.sleep(500)
//                        val percentage = i * 100
//                        handler.post{
//                            //update ui in main thread
//                            if (percentage == 100){
//                                tvStatus.setText(R.string.task_completed)
//                            }else{
//                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
//                            }
//                        }
//                    }
//                }catch (e: InterruptedException){
//                    e.printStackTrace()
//                }
//            }

            /**
             * background thread menggunakan coroutines
             *
             * lifecycleScope merupakan scope yang sudah disediakan library lifecycle-runtime-ktx
             * untuk menjalankan coroutine pada Activity yang sudah aware dengan lifecycle.
             * Dengan begitu instance coroutine akan otomatis dihapus ketika aplikasi dalam keadaan
             * onDestroy sehingga aplikasi tidak mengalami memory leak (kebocoran memori).
             *
             * Selanjutnya di sini kita menggunakan method launch karena kita akan memulai background
             * process tanpa nilai kembalian alias fire and forget
             *
             *  scope Dispatchers.Default dipilih karena kita akan melakukan proses biasa di Background
             *  Thread yang tidak memerlukan proses read-write.
             */
            lifecycleScope.launch(Dispatchers.Default){
                //simulate process in background thread
                for (i in 0..10){
                    /**
                     * delay adalah method khusus yang berasal dari library coroutine.
                     * Secara sekilas memiliki fungsi yang hampir mirip dengan Thread.Sleep,
                     * namun secara fundamental mereka sangatlah berbeda. Jika kita menggunakan
                     * Thread.Sleep yaitu dia akan mem-block proses secara keseluruhan.
                     * Sedangkan jika kita menggunakan delay, yang berhenti hanya yang di dalam coroutine
                     * itu saja, sedangkann coroutine lain masih bisa dijalankan.
                     */
                    delay(500)
                    val percentage = i * 10
                    /**
                     * Di sini kita menggunakan withContext(Dispatchers.Main) karena kita perlu pindah
                     * ke Main Thread untuk update UI berupa TextView, jika tidak menggunakan ini,
                     * maka UI/TextView tidak akan pernah ter-update.
                     */
                    withContext(Dispatchers.Main){
                        //update ui in main thread
                        if (percentage == 100){
                            tvStatus.setText(R.string.task_completed)
                        }else{
                            tvStatus.text = String.format(getString(R.string.compressing), percentage)
                        }
                    }
                }
            }
        }
    }
}