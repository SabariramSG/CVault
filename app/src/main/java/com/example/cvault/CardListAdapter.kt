package com.example.cvault

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cvault.databinding.ItemCardDetailsBinding

class CardListAdapter(
    private val websites: List<List<String>>?,
    private val onItemClicked: ((item: String) -> Unit)?
) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardListAdapter.ViewHolder {
        val binding = ItemCardDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardListAdapter.ViewHolder, position: Int) {
        websites?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return websites?.size ?: 0
    }

    inner class ViewHolder(private val binding: ItemCardDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: List<String>) {
            binding.cardName.text = item[0]
            binding.itemCard.setOnClickListener { onItemClicked?.invoke(item[0]) }
        }
    }
}
