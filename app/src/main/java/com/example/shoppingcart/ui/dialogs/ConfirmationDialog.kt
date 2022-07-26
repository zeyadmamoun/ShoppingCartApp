package com.example.shoppingcart.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.ConfirmationDialogBinding
import com.example.shoppingcart.models.ProductModel
import com.example.shoppingcart.ui.SharedViewModel

class ConfirmationDialog(private var product: ProductModel ,
                         private var list: MutableList<ProductModel>) : DialogFragment() {

    private var _binding : ConfirmationDialogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialoge_background_corners)
        _binding = DataBindingUtil.inflate(inflater, R.layout.confirmation_dialog,container,false)
        bind(product)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.6).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.setCancelable(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBtn.setOnClickListener {
            sharedViewModel.checkIfProductExist(product,list)
            sharedViewModel.getTotalPrice()
            sharedViewModel.increaseExpectedWeight(product.productWeight).also { dialog?.dismiss() }
        }

        binding.cancelBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun bind(productModel: ProductModel){
        binding.product = productModel
        binding.executePendingBindings()
    }
}