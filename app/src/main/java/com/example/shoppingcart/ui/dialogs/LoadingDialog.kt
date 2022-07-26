package com.example.shoppingcart.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.CustomLoadingDialogBinding
import com.example.shoppingcart.databinding.CustomWeightDialogBinding

class LoadingDialog : DialogFragment() {

    private var _binding : CustomLoadingDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialoge_background_corners)
        _binding = DataBindingUtil.inflate(inflater, R.layout.custom_loading_dialog,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.5).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.setCancelable(false)
    }

    override fun dismiss() {
        dialog?.dismiss()
    }
}