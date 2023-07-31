package com.example.myquote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myquote.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRandomQuote()

        binding.btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListQuotesActivity::class.java))
        }

    }

    /**
     * fungsi untuk mengambil data random quote dari API
     */
    private fun getRandomQuote(){
        /**
         * memunculkan progress bar
         */
        binding.progressBar.visibility = View.VISIBLE
        /**
         * untuk menggunakan library LoopJ, maka harus membuat object dari AsyncHttpClient
         */
        val client = AsyncHttpClient()
        /**
         * mendeklarasikan url API
         */
        val url = "https://quote-api.dicoding.dev/random"
        /**
         * melakukan fungsi get saat menggunakan API
         */
        client.get(url, object :AsyncHttpResponseHandler(){
            /**
             * onSuccess digunakan jika koneksi dengan API berhasil
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                /**
                 * jika koneksi berhasil
                 *
                 * jika proses get data api berhasil maka progress bar disembunyikan
                 */
                binding.progressBar.visibility = View.INVISIBLE

                val result = String(responseBody)
                /**
                 * Di sini Anda membuat Log terlebih dahulu untuk menampilkan response di Logcat.
                 * Hal ini penting untuk mengecek masalah apabila data tidak tampil.
                 * Jika data yang di Logcat ada tapi list tidak tampil,
                 * maka kemungkinan kesalahan terjadi pada saat parsing JSON atau pada saat menampilkan RecyclerView.
                 */
                Log.d(TAG, result)
                try {
                    /**
                     * karna data api dalam bentuk object (dibungkus dengan kurung kurawal) maka gunakan
                     * JSONObject untuk menangkap data API
                     */
                    val responseObject = JSONObject(result)

                    /**
                     * ambil data en dan author dari variable responseObject
                     */
                    val quote = responseObject.getString("en")
                    val author = responseObject.getString("author")

                    binding.tvQuote.text = quote
                    binding.tvAuthor.text = author
                }catch (e: Exception){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            /**
             * onFailure digunakan jika koneksi dengan API gagal
             */
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                /**
                 * jika koneksi gagal
                 *
                 * menyembunyikan progress bar dan menampilkan errorMessage pada Toast
                 */
                binding.progressBar.visibility = View.INVISIBLE

                val errorMessage = when(statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object{
        private val TAG = MainActivity::class.java.simpleName
    }
}