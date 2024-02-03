package com.vozniak.shoplistwithclenarchitecture.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vozniak.shoplistwithclenarchitecture.databinding.ItemShopEnabledBinding
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemShopEnabledBinding.bind(view)

    fun bindView(shopItem: ShopItem) {
        binding.tvText.text = shopItem.name
        binding.tvCount.text = shopItem.id.toString()
    }

}