package com.cotta.diego.kspadapter

import com.cotta.diego.kspadapter.databinding.ItemViewBinding

//@RecyclerViewAdapter(name = "Item")
fun ItemViewBinding.bind(item: ItemData, onClick: (ItemData) -> Unit) {
    tvTitle.text = item.title
    tvSubtitle.text = item.subtitle
    root.setOnClickListener { onClick.invoke(item) }
}