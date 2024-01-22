package com.vozniak.shoplistwithclenarchitecture.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.vozniak.shoplistwithclenarchitecture.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
   private val viewModel : MainViewModel by lazy {
       ViewModelProvider(this)[MainViewModel::class.java]
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}