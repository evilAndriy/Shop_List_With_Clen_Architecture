package com.vozniak.shoplistwithclenarchitecture.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vozniak.shoplistwithclenarchitecture.data.ShopListRepositoryImpl
import com.vozniak.shoplistwithclenarchitecture.databinding.ActivityMainBinding
import com.vozniak.shoplistwithclenarchitecture.domain.DeleteShopItemUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.EditShopItemUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.GetShopListUseCase
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        viewModel.shopList.observe(this, {
            shopListAdapter.shopList = it
        })

    }

    private fun setUpRecyclerView() {
        shopListAdapter = ShopListAdapter()
        binding.recyclerView.adapter = shopListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

}

