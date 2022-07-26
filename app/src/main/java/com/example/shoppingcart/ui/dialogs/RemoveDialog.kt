package com.example.shoppingcart.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.RemoveConfirmationDialogBinding
import com.example.shoppingcart.models.ProductModel
import com.example.shoppingcart.ui.SharedViewModel

class RemoveDialog(private var product: ProductModel) : DialogFragment() {
    private var _binding : RemoveConfirmationDialogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialoge_background_corners)
        _binding = DataBindingUtil.inflate(inflater, R.layout.remove_confirmation_dialog,container,false)
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

        dialog?.setCancelable(true)

        product.printProductDetails()

        binding.removeBtn.setOnClickListener {
            sharedViewModel.let {
                product.productUnit = product.productUnit-1
                if (product.productUnit == 0){
                    it.list.remove(product)
                }
                it.decreaseExpectedWeight(product.productWeight)
                it.assignProductsList()
                it.getTotalPrice().also { dialog?.dismiss() }
            }
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