package com.example.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecyclerview.databinding.ItemRowHeroBinding

/**
 * class adapter digunakan untuk mengatur bagaiman tiap elemen view ditampilkan di recycler view
 */
class ListHeroAdapter(private val listHero: ArrayList<Hero>): RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    /**
     * Kelas ListViewHolder digunakan sebagai ViewHolder dalam RecyclerView.
     * ViewHolder adalah wrapper seperti View yang berisi layout untuk setiap item dalam daftar RecyclerView.
     * Di sinilah tempat untuk menginisialisasi setiap komponen pada layout item dengan menggunakan itemView.findViewById.
     * Adapter akan membuat objek ViewHolder seperlunya, serta menetapkan data untuk tampilan tersebut.
     * Proses yang mengatribusikan tampilan ke datanya disebut binding.
     */
    class ListViewHolder(var binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    /**
     * Fungsi onCreateViewHolder() digunakan untuk membuat ViewHolder baru yang berisi layout item yang digunakan,
     * dalam hal ini yaitu R.layout.item_row_hero.
     * Metode ini membuat serta menginisialisasi ViewHolder dan View yang akan diatribusikan.
     * Namun, pada fungsi ini tidak bertujuan mengisi konten tampilan, sehingga belum terikat dengan data tertentu
     */
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListHeroAdapter.ListViewHolder {
        val binding =
            ItemRowHeroBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    /**
     * Fungsi onBindViewHolder() digunakan untuk menetapkan data yang ada ke dalam ViewHolder
     * sesuai dengan posisinya dengan menggunakan listHero[position].
     * Misalnya, jika Recyclerview yang dibuat bertujuan untuk menampilkan daftar nama,
     * metode tersebut mungkin menemukan nama yang sesuai, kemudian menetapkannya pada widget TextView yang ada dalam ViewHolder.
     */
    override fun onBindViewHolder(holder: ListHeroAdapter.ListViewHolder, position: Int) {
        val (name, description, photo) = listHero[position]
        Glide.with(holder.itemView.context)
            .load(photo) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(holder.binding.imgItemPhoto) // imageView mana yang akan diterapkan
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDes.text = description
        /**
         * menambahkan fungsi onclick setiap itemnnya ditekan
         *
         * Kita menggunakan holder.itemView karena kita ingin memberikan aksi ketika keseluruhan item ditekan.
         * Apabila Anda ingin menempatkan aksi pada view tertentu saja seperti foto,
         * Anda dapat menggunakan kode seperti holder.imgPhoto.setOnClickListener.
         */
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHero[holder.adapterPosition]) }
    }

    /**
     * Fungsi getItemCount() digunakan untuk menetapkan ukuran dari list data yang ingin ditampilkan.
     * Karena kita ingin menampilkan semua data, maka kita menggunakan listHero.size untuk mengetahui jumlah data pada list.
     */
    override fun getItemCount(): Int = listHero.size

    /**
     * Di sini kita menambahkan interface supaya ketika memanggil fungsi setOnItemClickCallback
     * terdapat data yang dikembalikan. Untuk memasukkan data ke interface,
     * kita cukup memasukkan data ke dalam fungsi onClicked di dalam onBindViewHolder ketika itemView ditekan.
     */
    interface OnItemClickCallback {
        fun onItemClicked(data: Hero)
    }


}

