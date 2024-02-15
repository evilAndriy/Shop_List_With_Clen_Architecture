package com.vozniak.shoplistwithclenarchitecture.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vozniak.shoplistwithclenarchitecture.R
import com.vozniak.shoplistwithclenarchitecture.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel
    var fragment_holder: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragment_holder = findViewById(R.id.fragment_holder)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setUpRecyclerView()
        viewModel.shopList.observe(this, {
            shopListAdapter.submitList(it)
        })
        initAddButton()

    }

    fun paneMode(): Boolean {
        return fragment_holder == null
    }

    fun initFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initAddButton() {
        binding.floatingActionButton.setOnClickListener() {
            if (paneMode()) {
                val intent = ShopListActivity.newAddIntent(this@MainActivity)
                startActivity(intent)
            } else initFragment(ShopItemFragment.newInstanceAddFragment())

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
            if (paneMode()) {
                val intent = ShopListActivity.newEditIntent(this@MainActivity, it.id)
                startActivity(intent)
            } else initFragment(ShopItemFragment.newInstanceEditFragment(it.id))
        }
    }

    private fun setUpLongClickListener() {
        shopListAdapter.shopItemLongClickVal = {
            viewModel.changeEnabledState(it)
        }
    }

    override fun initFinish() {
        supportFragmentManager.popBackStack()
        Toast.makeText(this@MainActivity, getString(R.string.good_job), Toast.LENGTH_SHORT).show()
    }


}


