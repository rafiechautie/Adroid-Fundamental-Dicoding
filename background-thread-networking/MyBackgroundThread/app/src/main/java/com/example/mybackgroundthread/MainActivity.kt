package com.example.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
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
            executor.execute{
                try {
                    //simulate proccess compressing
                    for (i in 0..10) {
                        Thread.sleep(500)
                        val percentage = i * 100
                        handler.post{
                            //update ui in main thread
                            if (percentage == 100){
                                tvStatus.setText(R.string.task_completed)
                            }else{
                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
                            }
                        }
                    }
                }catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
    }
}