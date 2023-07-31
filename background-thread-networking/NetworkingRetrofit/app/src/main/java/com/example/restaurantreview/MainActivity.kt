package com.example.restaurantreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurantreview.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * menyembuntikan action bar pada halaman MainActivity
         */
        supportActionBar?.hide()

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

        findRestaurant()

        /**
         * jalankan kode di dalam kurung kurawal ketika btnSend diclick
         */
        binding.btnSend.setOnClickListener { view ->
            /**
             * data yang diisi di parameter postReview berasal dari text yang diinput user di edReview
             */
            postReview(binding.edReview.text.toString())
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
     * function untuk mengepost review ke API
     */
    private fun postReview(review: String) {
        /**
         * tampilkan progress barr
         */
        showLoading(true)
        /**
         * menjalankan fungsi postReview
         */
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)
        client.enqueue(object : retrofit2.Callback<PostReviewResponse>{
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                /**
                 * menyembunyikan progress barr
                 */
                showLoading(false)
                /**
                 * mengambil body (isi) respons dari permintaan API dan menyimpannya ke dalam variabel responseBody
                 */
                val responseBody = response.body()
                /**
                 * jika response dari API berhasil dan responseBody tidak kosong
                 */
                if (response.isSuccessful && responseBody != null) {
                    setReviewData(responseBody.customerReviews)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun findRestaurant() {
        showLoading(true)
        val client = ApiConfig.getApiService().getRestaurantDetail(RESTAURANT_ID)
        /**
         *  fungsi enqueue untuk menjalankan request secara asynchronous di background. Sehingga aplikasi
         *  tidak freeze/lag ketika melakukan request. Kemudian, hasilnya terdapat dua callback,
         *  yakni onResponse ketika ada respon, dan onFailure ketika gagal. Ketika terdapat respon,
         *  bisa jadi terjadi kegagalan seperti eror 404 atau 500, maka untuk mendapatkan data ketika berhasil,
         *  kita mengeceknya melalui response.isSuccessful() untuk mengetahui apakah server mengembalikan
         *  kode 200 (OK) atau tidak. Untuk datanya sendiri dapat diambil di response.body().
         *
         *  Seperti yang Anda lihat, di sini kita tidak perlu melakukan parsing lagi,
         *  karena data yang didapat sudah berupa POJO.  Proses parsing dilakukan secara otomatis
         *  oleh Retrofit dengan menggunakan kode .addConverterFactory(GsonConverterFactory.create())
         *  di bagian ApiConfig dan anotasi SerializedName pada masing-masing POJO.
         */
        client.enqueue(object : retrofit2.Callback<RestaurantDetailResponse>{
            override fun onResponse(
                call: Call<RestaurantDetailResponse>,
                response: Response<RestaurantDetailResponse>
            ) {
                showLoading(false)
                /**
                 * jika respon berhasil
                 */
                if (response.isSuccessful) {
                    /**
                     * mengambil body (isi) respons dari permintaan API dan menyimpannya ke dalam variabel responseBody
                     */
                    val responseBody = response.body()
                    /**
                     * kondisi untuk mengecek apakah nilai responseBody tidak null atau tidak kosong.
                     * Jika responseBody tidak null, blok kode di dalamnya akan dijalankan.
                     */
                    if (responseBody != null) {
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant.customerReviews)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantDetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    /**
     * fungsi untuk mengambil data review dan menempelkan ke recyclerview
     */
    private fun setReviewData(customerReviews: List<CustomerReviewsItem>) {
        /**
         * membuat objek ArrayList kosong yang akan diisi dengan data ulasan pelanggan.
         */
        val listReview = ArrayList<String>()
        /**
         * mengulang setiap item dalam daftar customerReviews, kemudian mengekstrak nilai
         * review dan name dari setiap item dan menambahkan baris teks ke listReview.
         */
        for (review in customerReviews) {
            listReview.add(
                """
                ${review.review}
                - ${review.name}
                """.trimIndent()
            )
        }
        /**
         * membuat objek adapter ReviewAdapter yang mengambil daftar ulasan pelanggan sebagai argumen.
         */
        val adapter = ReviewAdapter(listReview)
        binding.rvReview.adapter = adapter
        /**
         * mengatur teks pada EditText dengan teks kosong.
         */
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

    companion object {
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }
}