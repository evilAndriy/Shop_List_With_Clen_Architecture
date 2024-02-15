package com.vozniak.shoplistwithclenarchitecture.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vozniak.shoplistwithclenarchitecture.R
import com.vozniak.shoplistwithclenarchitecture.databinding.ActivityShopListBinding
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem

class ShopListActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var binding: ActivityShopListBinding

    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        launchRightMode()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_AD -> ShopItemFragment.newInstanceAddFragment()
            MODE_EDIT -> ShopItemFragment.newInstanceEditFragment(shopItemId)
            else -> null
        }

        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.item_holder, it)
                .commit()
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

    override fun initFinish() {
        finish()
        Toast.makeText(this, getString(R.string.good_job), Toast.LENGTH_SHORT).show()
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
