package com.example.myquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquote.databinding.ActivityListQuotesBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class ListQuotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListQuotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * memasang agar actionbar titlenya adalah list of quotes
         */
        supportActionBar?.title = "List Of Quotes"

        val layoutManager = LinearLayoutManager(this)
        binding.listQuotes.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listQuotes.addItemDecoration(itemDecoration)

        getListQuotes()

    }

    /**
     * function untuk mengambil list quote dari API
     */
    private fun getListQuotes() {
        /**
         * memunculkan progressBar
         */
        binding.progressBar.visibility = View.VISIBLE
        /**
         * untuk menggunakan library LoopJ, maka harus membuat object dari AsyncHttpClient
         */
        val client = AsyncHttpClient()
        /**
         * mendeklarasikan url API
         */
        val url = "https://quote-api.dicoding.dev/list"
        /**
         * melakukan fungsi get saat menggunakan API
         */
        client.get(url, object : AsyncHttpResponseHandler(){
            /**
             * onSuccess digunakan jika koneksi dengan API berhasil
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                /**
                 * jika data berhasil diambil maka progressBar disembuntikan
                 */
                binding.progressBar.visibility = View.INVISIBLE

                val listQuote = ArrayList<String>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    /**
                     * karena data api dimulai dari "[]" maka gunakan JSONArray
                     */
                    val jsonArray = JSONArray(result)


                    /**
                     * mengambil list data API
                     */
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val quote = jsonObject.getString("en")
                        val author = jsonObject.getString("author")
                        listQuote.add("\n$quote\n — $author\n")
                    }

                    /**
                     * memasukkan data yang telah diambil ke adapter recyvlerView
                     */
                    val adapter = QuoteAdapter(listQuote)
                    binding.listQuotes.adapter = adapter
                }catch (e: Exception){
                    Toast.makeText(this@ListQuotesActivity, e.message, Toast.LENGTH_SHORT).show()
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
                 * jika proses get data api gagal, progressBar disembuntikan dan menampilkan error message pada Toast
                 */
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@ListQuotesActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object{
        private val TAG = ListQuotesActivity::class.java.simpleName
    }
}