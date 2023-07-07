package com.example.cvault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cvault.bo.CardDetails
import com.example.cvault.databinding.ItemCardDetailsBinding

class CardListAdapter(
    private val websites: List<CardDetails>?,
    private val onItemClicked: ((item: String?) -> Unit)?
) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemCardDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        websites?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return websites?.size ?: 0
    }

    inner class ViewHolder(private val binding: ItemCardDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card : CardDetails) {
            binding.cardName.text = card.name
            Glide.with(binding.cardImage).load(card.imageURL).into(binding.cardImage)
            binding.itemCard.setOnClickListener { onItemClicked?.invoke(card.imageURL) }
        }
    }
}
