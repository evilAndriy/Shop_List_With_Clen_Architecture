package com.vozniak.shoplistwithclenarchitecture.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vozniak.shoplistwithclenarchitecture.R
import com.vozniak.shoplistwithclenarchitecture.databinding.ItemShopDisabledBinding
import com.vozniak.shoplistwithclenarchitecture.databinding.ItemShopEnabledBinding
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem



class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var shopItemLongClickVal: ((ShopItem) -> Unit)? = null
    var shopItemClickVal: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            FIRST_ENABLED_ITEM -> R.layout.item_shop_enabled
            SECOND_DISABLED_ITEM -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view Type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.bindView(shopItem)
        holder.view.setOnLongClickListener() {
            shopItemClickVal?.invoke(shopItem)
            true
        }
        holder.view.setOnLongClickListener() {
            shopItemLongClickVal?.invoke(shopItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val value = getItem(position)
        val result = if (value.enabled) {
            FIRST_ENABLED_ITEM
        } else {
            SECOND_DISABLED_ITEM
        }
        return result
    }

    companion object {
        const val FIRST_ENABLED_ITEM = 1
        const val SECOND_DISABLED_ITEM = 2
        const val Max_POOL_RECYCLER = 20
    }

}