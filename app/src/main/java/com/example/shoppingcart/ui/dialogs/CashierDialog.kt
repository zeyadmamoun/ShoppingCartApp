package com.example.shoppingcart.ui.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.CashierDialogBinding
import com.example.shoppingcart.ui.SharedViewModel
import kotlinx.coroutines.*

class CashierDialog: DialogFragment() {
    private var _binding : CashierDialogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialoge_background_corners)
        _binding = DataBindingUtil.inflate(inflater, R.layout.cashier_dialog,container,false)
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var paymentSuccess = false

        lifecycleScope.launch(Dispatchers.IO) {
            val offlineRequest = async { sharedViewModel.postBillItems("offline") }
            val waitedOfflineRequest = offlineRequest.await()

            if (waitedOfflineRequest != null){
                Log.d("productCode", waitedOfflineRequest.id.toString())

                while (!paymentSuccess){
                    val request = async { sharedViewModel.getStatus(waitedOfflineRequest.id) }
                    val waitedRequest = request.await()

                    Log.d("productCode", waitedRequest?.success.toString())

                    if (waitedRequest != null){
                        paymentSuccess = waitedRequest.success
                    }
                    delay(5000)
                }

                withContext(Dispatchers.Main){
                    binding.textView.visibility = View.GONE
                    binding.taskCompleteAnimation.visibility = View.VISIBLE
                    binding.btnEnd.visibility = View.VISIBLE
                }
            }
        }

        binding.btnEnd.setOnClickListener {
            dialog?.dismiss()
            val action = CashierDialogDirections.actionCashierDialogToStartFragment()
            view.findNavController().navigate(action)
        }
    }
}