package com.example.restaurantreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurantreview.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * menyembuntikan action bar pada halaman MainActivity
         */
        supportActionBar?.hide()

        /**
         * mendapatkan value dari LiveData yang ada pada kelas ViewModel
         */
        viewModel.restaurant.observe(this, { restaurant ->
            setRestaurantData(restaurant)
        })


        /**
         * kode dibawah ini berfungsi untuk menampilkan daftar item ke recyvler view
         */
        /**
         * Kode ini digunakan untuk membuat objek LinearLayoutManager yang akan mengatur tata letak
         * item di dalam RecyclerView. this di sini merujuk pada konteks aplikasi saat ini.
         * Objek ini akan menentukan apakah item dalam RecyclerView akan ditampilkan dalam satu baris
         * horizontal atau satu kolom vertikal.
         */
        val layoutManager = LinearLayoutManager(this)
        /**
         * Setelah objek layoutManager dibuat, kita memasukkannya ke dalam RecyclerView menggunakan
         * properti layoutManager. Dalam kode ini, binding.rvReview mengacu pada RecyclerView yang telah
         * diikat ke tampilan melalui data binding.
         */
        binding.rvReview.layoutManager = layoutManager
        /**
         * Kode ini digunakan untuk membuat objek DividerItemDecoration yang akan menambahkan garis
         * pemisah antara setiap item dalam RecyclerView. Parameter pertama adalah konteks aplikasi
         * saat ini, dan parameter kedua adalah orientasi layout manager.
         */
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        /**
         * Akhirnya, objek itemDecoration ditambahkan ke RecyclerView menggunakan metode addItemDecoration().
         * Dalam kode ini, binding.rvReview mengacu pada RecyclerView yang telah diikat ke tampilan melalui data binding.
         */
        binding.rvReview.addItemDecoration(itemDecoration)

        /**
         * mendapatkan value dari LiveData yang ada pada kelas ViewModel
         *
         * customerReviews akan selalu diperbarui secara realtime sesuai dengan perubahan yang ada
         * di kelas ViewModel. Contohnya yaitu saat Anda menambahkan data review baru,
         * alih alih memanggil fungsi findRestaurant lagi, Anda cukup mengobservasi LiveData
         * tersebut untuk selalu mendapatkan data terbaru.
         */
        viewModel.listReview.observe(this, { consumerReviews ->
            setReviewData(consumerReviews)
        })

        viewModel.snackbarText.observe(this, {
            /**
             * ntuk mengambil data class Event tersebut, cukup panggil fungsi getContentIfNotHandled seperti dibawah ini
             *
             * Secara otomatis ketika aksi sudah pernah dilakukan sebelumnya, maka ia akan menghasilkan null.
             */
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        /**
         * mendapatkan value dari live data yang ada pada kelas ViewModel
         */
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        /**
         * jalankan kode di dalam kurung kurawal ketika btnSend diclick
         */
        binding.btnSend.setOnClickListener { view ->
            /**
             * data yang diisi di parameter postReview berasal dari text yang diinput user di edReview
             */
            viewModel.postReview(binding.edReview.text.toString())
            /**
             * code dibawah digunakan untuk mendapatkan objek InputMethodManager,
             * yang memungkinkan untuk mengelola keyboard virtual pada perangkat Android.
             */
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            /**
             * Kode imm.hideSoftInputFromWindow(view.windowToken, 0) digunakan untuk menyembunyikan
             * keyboard virtual dari layar. Metode hideSoftInputFromWindow() mengambil dua parameter, yaitu
             *
             * view.windowToken: token jendela saat ini, yang digunakan untuk menunjukkan keyboard virtual mana yang harus disembunyikan.
             * 0: opsi tambahan yang dapat diatur sebagai nol.
             *
             * Kode tersebut sering digunakan ketika Anda ingin menyembunyikan keyboard setelah pengguna selesai
             * memasukkan teks dalam sebuah formulir atau interaksi input lainnya. Hal ini dapat meningkatkan
             * pengalaman pengguna dengan membebaskan area layar yang biasanya ditempati oleh keyboard,
             * sehingga pengguna dapat melihat lebih banyak konten dan memperbesar interaksi dengan elemen lain pada layar.
             */
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * fungsi untuk mengambil data review dan menempelkan ke recyclerview
     */
    private fun setReviewData(customerReviews: List<CustomerReviewsItem>) {

        val listReview = customerReviews.map {
            "${it.review}\n- ${it.name}"
        }
        val adapter = ReviewAdapter(listReview)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        //tarok data api ke view
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}