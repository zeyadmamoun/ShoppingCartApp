package com.example.shoppingcart.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.shoppingcart.R
import com.example.shoppingcart.adapters.BillAdapter
import com.example.shoppingcart.databinding.FragmentBillBinding
import com.example.shoppingcart.ui.SharedViewModel
import com.example.shoppingcart.ui.dialogs.CashierDialog
import com.example.shoppingcart.ui.dialogs.PaymentGatewayDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillFragment : Fragment() {

    private var _binding : FragmentBillBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill, container,false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the SharedViewModel
        binding.viewModel = sharedViewModel
        binding.billRecyclerView.adapter = BillAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOnlinePayment.setOnClickListener {
            val dialog = PaymentGatewayDialog()
            dialog.show(parentFragmentManager,"PaymentGatewayDialog")
        }

        binding.btnCasherPayment.setOnClickListener {
            val dialog = CashierDialog()
            dialog.show(parentFragmentManager,"CashierDialog")
        }
    }
}