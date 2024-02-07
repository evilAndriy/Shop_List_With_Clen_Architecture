package com.vozniak.shoplistwithclenarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vozniak.shoplistwithclenarchitecture.data.ShopListRepositoryImpl
import com.vozniak.shoplistwithclenarchitecture.domain.AddShopItemUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.EditShopItemUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.GetShopItemUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() {
            return _errorInputName
        }

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() {
            return _errorInputCount
        }

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() {
            return _shopItem
        }

    private val _finish = MutableLiveData<Unit>()
    val finish: LiveData<Unit>
        get() {
            return _finish
        }

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopList(item)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

     fun resetErrorInputName() {
        _errorInputName.value = false
    }

     fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _finish.value = Unit
    }

}