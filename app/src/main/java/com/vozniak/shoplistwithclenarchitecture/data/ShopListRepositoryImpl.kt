package com.vozniak.shoplistwithclenarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem
import com.vozniak.shoplistwithclenarchitecture.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0


    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }

    shopList.add(shopItem)
        updateList()
}

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopList(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }
    private fun updateList(){
        shopListLD.value = shopList.toList()
    }
}