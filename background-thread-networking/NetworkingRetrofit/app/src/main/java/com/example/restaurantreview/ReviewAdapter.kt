package com.example.restaurantreview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreview.databinding.ItemReviewBinding

class ReviewAdapter(private val listReview: List<String>): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ReviewAdapter.ViewHolder, position: Int) {
        viewHolder.binding.tvItem.text = listReview[position]
    }

    override fun getItemCount() = listReview.size
}