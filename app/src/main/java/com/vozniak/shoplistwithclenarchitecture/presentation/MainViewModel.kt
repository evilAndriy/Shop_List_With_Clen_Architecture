package com.vozniak.shoplistwithclenarchitecture.presentation

import androidx.lifecycle.ViewModel
import com.vozniak.shoplistwithclenarchitecture.data.ShopListRepositoryImpl
import com.vozniak.shoplistwithclenarchitecture.domain.DeleteShopItemUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.EditShopItemUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.GetShopListUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem

class MainViewModel: ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopListUseCase = GetShopListUseCase(repository)

    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()


    fun deleteShopList(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)

    }

    fun changeEnabledState(shopItem: ShopItem) {
        var newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopList(newItem)

    }
}
