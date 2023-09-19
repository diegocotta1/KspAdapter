package com.cotta.diego.kspadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cotta.diego.kspadapter.databinding.ItemViewBinding

class UserListItemViewHolder(
    private var binding: ItemViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemData, onClick: (ItemData) -> Unit) {
        binding.tvTitle.text = item.title
        binding.tvSubtitle.text = item.subtitle
        binding.root.setOnClickListener { onClick.invoke(item) }

    }

    companion object {
        fun from(parent: ViewGroup): UserListItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
            return UserListItemViewHolder(binding)
        }
    }
}