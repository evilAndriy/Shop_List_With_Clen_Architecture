package com.vozniak.shoplistwithclenarchitecture.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setUpRecyclerView()
        viewModel.shopList.observe(this, {
            shopListAdapter.submitList(it)
        })
     initAddButton()
    }

    private fun initAddButton() {
        binding.floatingActionButton.setOnClickListener() {
            val intent = ShopListActivity.initAddIntent(this@MainActivity)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView() {
        shopListAdapter = ShopListAdapter()
        binding.recyclerView.adapter = shopListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.FIRST_ENABLED_ITEM,
            ShopListAdapter.Max_POOL_RECYCLER
        )
        binding.recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.SECOND_DISABLED_ITEM,
            ShopListAdapter.Max_POOL_RECYCLER
        )
//
        setUpLongClickListener()
        setUpClickListener()
        setUpSwipeListener()
    }

    private fun setUpSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopList(item)
            }


        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setUpClickListener() {
        shopListAdapter.shopItemClickVal = {

        }
    }

    private fun setUpLongClickListener() {
        shopListAdapter.shopItemLongClickVal = {
            viewModel.changeEnabledState(it)
        }
    }


}


