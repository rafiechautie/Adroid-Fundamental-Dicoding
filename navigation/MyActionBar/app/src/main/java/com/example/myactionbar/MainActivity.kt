package com.example.myactionbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
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