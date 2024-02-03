package com.vozniak.shoplistwithclenarchitecture.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vozniak.shoplistwithclenarchitecture.R
import com.vozniak.shoplistwithclenarchitecture.databinding.ActivityShopListBinding

class ShopListActivity : AppCompatActivity() {
    lateinit var binding: ActivityShopListBinding
//    private lateinit var viewModel: ShopItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mod"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_AD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        fun initAddIntent(context: Context): Intent {
            val intent = Intent(context, ShopListActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_AD)
            }
            return intent
        }
    }
}