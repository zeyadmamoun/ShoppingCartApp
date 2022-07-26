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
import com.example.shoppingcart.databinding.PaymentGatewayDialogBinding
import com.example.shoppingcart.ui.SharedViewModel
import kotlinx.coroutines.*

class PaymentGatewayDialog: DialogFragment() {
    private var _binding : PaymentGatewayDialogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialoge_background_corners)
        _binding = DataBindingUtil.inflate(inflater,R.layout.payment_gateway_dialog,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.5).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.setCancelable(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var paymentSuccess = false

        lifecycleScope.launch(Dispatchers.IO) {

            val url = async { sharedViewModel.postBillItems("online") }
            val waitedUrl = url.await()

            if (waitedUrl != null){
                Log.d("productCode", waitedUrl.url)
                Log.d("productCode", waitedUrl.id.toString())

                withContext(Dispatchers.Main){
                    binding.qrProgressBar.visibility = View.GONE
                    binding.qrImageView.visibility = View.VISIBLE
                    sharedViewModel.generateQR(binding.qrImageView, waitedUrl.url)
                }

                while (!paymentSuccess){
                    val request = async { sharedViewModel.getStatus(waitedUrl.id) }
                    val waitedRequest = request.await()

                    Log.d("productCode", waitedRequest?.success.toString())

                    if (waitedRequest != null){
                        paymentSuccess = waitedRequest.success
                    }
                    delay(5000)
                }

                withContext(Dispatchers.Main){
                    binding.qrImageView.visibility = View.GONE
                    binding.textView2.visibility = View.GONE
                    binding.paymentCompleteAnimation.visibility = View.VISIBLE
                    binding.dismissBtn.visibility = View.VISIBLE
                }
            }
        }

        binding.dismissBtn.setOnClickListener {
            dialog?.dismiss()
            val action = PaymentGatewayDialogDirections.actionPaymentGatewayDialogToStartFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun dismiss() {
        dialog?.dismiss()
    }
}