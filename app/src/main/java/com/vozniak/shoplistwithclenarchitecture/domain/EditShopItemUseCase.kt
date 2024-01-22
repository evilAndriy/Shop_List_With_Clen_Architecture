package com.vozniak.shoplistwithclenarchitecture.domain

class EditShopItemUseCase(private val shopListRepository:ShopListRepository) {
    fun editShopList(shopItem: ShopItem){
        shopListRepository.editShopList(shopItem)
    }
}