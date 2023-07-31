package com.example.myactionbar

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.myactionbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * untuk menampilkan action bar atau toolbar harus mengoverride kedua fungsi onCreateOptionsMenu dan
     * onOptionsItemSelected
     */

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        /**
         * mengambil komponen searchview yang sudah di inflate
         */
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView


        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        /**
         * SetQueryHint() berguna untuk memberikan hint pada pengguna tentang query search apa yang harus dimasukkan
         */
        searchView.queryHint = resources.getString(R.string.search_hint)

        /**
         * ketika button search ditekan maka code didalamnya akan dijalankan
         */
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            /**
             * kode dibawah ini akan dijalankan ketika pengguna menekan button sumbit
             */
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return false
            }

            /**
             * onQueryTextChange digunakan setiap kali user memasukkan atau mengubah query yang ada pada inputan searchview.
             */
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    /**
     * fungsi onOptionsItemSelected digunakan untuk menangani kejadian ketika pengguna menyentuh salah satu item di
     * tollbar/actionbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu1 -> {
                /**
                 * jika menu1 di click maka akan memasang MenuFragment diatas MainActivity
                 */
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MenuFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.menu2 -> {
                /**
                 * ketika menu2 di click maka akan mengarahkan halaman ke menu activity
                 */
                val i = Intent(this, MenuActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
}