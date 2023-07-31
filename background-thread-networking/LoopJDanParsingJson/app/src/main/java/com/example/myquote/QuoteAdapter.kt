package com.example.myquote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myquote.databinding.ItemQuoteBinding


class QuoteAdapter(private val listReview: ArrayList<String>): RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemQuoteBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): QuoteAdapter.ViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: QuoteAdapter.ViewHolder, position: Int) {
        viewHolder.binding.tvItem.text = listReview[position]
    }

    override fun getItemCount(): Int {
        return listReview.size
    }
}