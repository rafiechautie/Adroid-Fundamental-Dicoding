package com.example.mynavigationdrawer

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mynavigationdrawer.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    /**
     * delklarasi sekaligus mendefinisikan value circle image yang akan ditarok di nav_header_main
     */
    private lateinit var profileCircleImageView: CircleImageView
    private var profileImageUrl = "https://lh3.googleusercontent.com/-4qy2DfcXBoE/AAAAAAAAAAI/AAAAAAAABi4/rY-jrtntAi4/s640-il/photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        /**
         * Obyek fab diberikan listener onClickListener() untuk menampilkan sebuah Snackbar.
         * Snackbar adalah suksesor dari toast.
         * Namun tidak ada perbedaan di antara keduanya dari sisi penggunaan.
         * Perbedaan mendasar adalah pada Snackbar Anda bisa menambahkan sebuah action
         * untuk melakukan sebuah aksi tertentu. Hal ini tidak bisa dilakukan pada toast.
         */
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action") { Toast.makeText(this@MainActivity, "Halo ini action dari snackbar", Toast.LENGTH_SHORT).show()}
                .show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout

        /**
         * NavigationView menampung semua menu dan sebuah header
         */
        val navView: NavigationView = binding.navView
        /**
         * menampilkan gambar yang telah didefinisikan diatas
         *
         *  jika Anda ingin mengubah komponen view yang terdapat di dalam header sebuah navigation view,
         *  maka proses casting/inisialisasi komponen harus dilakukan dengan cara seperti ini:
         *
         *  Kenapa harus 0?
         *  Ini karena indeks header berada pada susunan teratas dari kumpulan list menu yang terdapat pada NavigationView.
         */
        profileCircleImageView = navView.getHeaderView(0).findViewById(R.id.imageView)
        Glide.with(this)
            .load(profileImageUrl)
            .into(profileCircleImageView)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /**
         * mendaftarkan id layout ke appBarConfiguration supaya icon hamburger tidak berubah menjadi tanda panah ketika
         * menu dibawah ini diclick
         */
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_cart
            ), drawerLayout
        )
        /**
         * setupActionBarWithNavController digunakan untuk mengatur judul AppBar agar sesuai dengan Fragment yang ditampilkan.
         */
        setupActionBarWithNavController(navController, appBarConfiguration)
        /**
         * setupWithNavController digunakan supaya NavigationDrawer menampilkan Fragment yang sesuai ketika menu dipilih.
         */
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * function onSupportNavigateUp berfungsi untuk mengatur ketika tombol back ditekan.
     * Misalnya ketika Anda di halaman CartFragment, jika Anda tekan tombol back,
     * maka aplikasi tidak langsung keluar,
     * melainkan akan menuju ke startDestination yang ada di Navigation Graph, yaitu HomeFragment.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}