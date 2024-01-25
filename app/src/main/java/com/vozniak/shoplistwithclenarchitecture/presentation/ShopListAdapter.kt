package com.vozniak.shoplistwithclenarchitecture.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vozniak.shoplistwithclenarchitecture.R
import com.vozniak.shoplistwithclenarchitecture.databinding.ItemShopDisabledBinding
import com.vozniak.shoplistwithclenarchitecture.databinding.ItemShopEnabledBinding
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val binding = ItemShopEnabledBinding.bind(view)

     fun bindView(shopItem: ShopItem){
         binding.tvText.text = shopItem.name
         binding.tvCount.text = shopItem.id.toString()
     }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_enabled,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(shopList[position])
    }
    override fun getItemCount(): Int {
       return shopList.size
    }
}