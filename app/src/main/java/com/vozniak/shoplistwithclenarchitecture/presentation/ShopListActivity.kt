package com.vozniak.shoplistwithclenarchitecture.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vozniak.shoplistwithclenarchitecture.R
import com.vozniak.shoplistwithclenarchitecture.databinding.ActivityShopListBinding
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem

class ShopListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopListBinding
    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        parseIntent()
        addTextChangeListeners()
        launchRightMode()
        observeViewModels()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_AD -> initAddMode()
            MODE_EDIT -> initEditMode()
        }
    }

    fun addTextChangeListeners() {
        binding.MyNameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.MyCountText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    fun observeViewModels() {
        viewModel.errorInputCount.observe(this) {
            val massage = if (it) {
                getString(R.string.errorCount)
            } else {
                null
            }
            binding.MyCountText.error = massage
        }
        viewModel.errorInputName.observe(this) {
            val massage = if (it) {
                getString(R.string.errorName)
            } else {
                null
            }
            binding.MyNameText.error = massage
        }
        viewModel.finish.observe(this) {
            finish()
        }
    }

    fun initAddMode() {
        binding.addButton.setOnClickListener() {
            viewModel.addShopItem(
                binding.MyNameText.text?.toString(),
                binding.MyCountText.text?.toString()
            )
        }
    }

    fun initEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this, {
            binding.MyNameText.setText(it.name)
            binding.MyCountText.setText(it.count.toString())
        })
        binding.addButton.setOnClickListener() {
            viewModel.editShopItem(
                binding.MyNameText.text?.toString(),
                binding.MyCountText.text?.toString()
            )
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("missing screen mode")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_AD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("missing ID")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }

    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mod"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_AD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val UNKNOWN_MODE = ""
        fun newAddIntent(context: Context): Intent {
            val intent = Intent(context, ShopListActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_AD)
            }
            return intent
        }

        fun newEditIntent(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopListActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
                putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            }
            return intent
        }
    }
}