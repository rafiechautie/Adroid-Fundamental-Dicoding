package com.example.myrecyclerview

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * disitu akan dibuat sebuah array yang akan menampung data Hero dan dimasukkan ke dalam variable list
     */
    private val list = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * kode dibawah menunjukkan ketika fixed size bernilai true maka RV
         * dapat melakukan optimatisasi ukuran lebar dan tinggi secara otomatis
         */
        binding.rvHeroes.setHasFixedSize(true)

        /**
         * memanggil data yang udah dibuat di resources string dan dimasukin ke variable list
         */
        list.addAll(listHeros)

        showRecycleList()
    }

    /**
     * fungsi untuk memberikan aksi ketika salah satu item di click
     */
    private fun showSelectedHero(hero: Hero) {
        Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
    }

    private val listHeros: ArrayList<Hero>
        get(){
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.getStringArray(R.array.data_photo)
            val listHero = ArrayList<Hero>()
            for (i in dataName.indices) {
                val hero = Hero(dataName[i],dataDescription[i], dataPhoto[i])
                listHero.add(hero)
            }
            return listHero
        }

    private fun showRecycleList(){
        /**
         * mengatur tampilan recycleview saat mode potrait atau landscape
         *
         * jika mode landscape maka dia akan menjadi grid yang punya 2 kolom
         *
         * jika mode potrait maka dia akan menjadi linear list yang punya 1 kolom
         */
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvHeroes.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        }

        val listHeroAdapter = ListHeroAdapter(list)
        binding.rvHeroes.adapter = listHeroAdapter

        /**
         * ketika salah satu item diclick maka function showSelectedHero akan dijalankan
         */
        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }
}