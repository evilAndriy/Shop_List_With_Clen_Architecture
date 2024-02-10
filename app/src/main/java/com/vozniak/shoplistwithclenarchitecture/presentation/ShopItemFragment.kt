package com.vozniak.shoplistwithclenarchitecture.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vozniak.shoplistwithclenarchitecture.R
import com.vozniak.shoplistwithclenarchitecture.databinding.FragmentShopItemBinding
import com.vozniak.shoplistwithclenarchitecture.domain.ShopItem

class ShopItemFragment : Fragment() {
    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID
    private lateinit var viewModel: ShopItemViewModel
    private lateinit var binding: FragmentShopItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        launchRightMode()
        addTextChangeListeners()
        observeViewModels()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_AD -> initAddMode()
            MODE_EDIT -> initEditMode()
            else -> throw RuntimeException("Unknown mode")
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
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val massage = if (it) {
                getString(R.string.errorCount)
            } else {
                null
            }
            binding.MyCountText.error = massage
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val massage = if (it) {
                getString(R.string.errorName)
            } else {
                null
            }
            binding.MyNameText.error = massage
        }
        viewModel.finish.observe(viewLifecycleOwner) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
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
        viewModel.shopItem.observe(viewLifecycleOwner, {
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

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("missing screen mode")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_AD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("missing ID")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }

    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mod"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_AD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val UNKNOWN_MODE = ""
        fun newInstanceAddFragment(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_AD)
                }
            }
        }

        fun newInstanceEditFragment(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}
