package com.vozniak.shoplistwithclenarchitecture.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vozniak.shoplistwithclenarchitecture.R
import com.vozniak.shoplistwithclenarchitecture.databinding.ActivityShopListBinding

class ShopListActivity : AppCompatActivity() {
    lateinit var binding : ActivityShopListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}