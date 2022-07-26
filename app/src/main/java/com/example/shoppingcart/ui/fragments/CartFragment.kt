package com.example.shoppingcart.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.shoppingcart.R
import com.example.shoppingcart.adapters.DeleteListener
import com.example.shoppingcart.adapters.OffersAdapter
import com.example.shoppingcart.adapters.ProductAdapter
import com.example.shoppingcart.databinding.FragmentCartBinding
import com.example.shoppingcart.models.ProductModel
import com.example.shoppingcart.ui.SharedViewModel
import com.example.shoppingcart.ui.dialogs.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() , DeleteListener{

    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private val adapter = ProductAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart,container,false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the SharedViewModel
        binding.viewModel = sharedViewModel

        adapter.setList(sharedViewModel.products.value)
        binding.userProductsRecyclerView.adapter = adapter
        binding.offersRecyclerView.adapter = OffersAdapter()

        sharedViewModel.reInitializeBarcodeReceiver()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.bluetooth?.ConnectedThread()?.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.products.observe(viewLifecycleOwner) {
            adapter.setList(sharedViewModel.products.value)
        }

        sharedViewModel.receivedBarcode.observe(viewLifecycleOwner){
            if (it != " "){
                addingProductProcess(it)
            }
            Log.d("bluetoothTag","expected weight is ${sharedViewModel.expectedWeight.value}")
        }

        sharedViewModel.expectedWeight.observe(viewLifecycleOwner){ expectedWeight ->
            var areEqual = false
            when {
                expectedWeight > sharedViewModel.actualWeight -> {
                    val dialog = AddProductDialog()
                    dialog.show(parentFragmentManager, "AddToCartDialog")
                    lifecycleScope.launch(Dispatchers.Default) {
                        while (!areEqual) {
                            areEqual = sharedViewModel.checkActualWeightWithExpectedWeight(sharedViewModel.actualWeight, expectedWeight)
                        }
                        withContext(Dispatchers.Main){
                            dialog.dismiss()
                        }
                    }
                }
                expectedWeight < sharedViewModel.actualWeight -> {
                    val dialog = WeightErrorDialog()
                    dialog.show(parentFragmentManager, "WeightErrorDialog")
                    lifecycleScope.launch(Dispatchers.Default) {
                        while (!areEqual) {
                            areEqual = sharedViewModel.checkActualWeightWithExpectedWeight(sharedViewModel.actualWeight, expectedWeight)
                        }
                        withContext(Dispatchers.Main){
                            dialog.dismiss()
                        }
                    }
                }
            }
        }

        binding.btnProceed.setOnClickListener {
            var areEqual = false
            when {
                sharedViewModel.expectedWeight.value!! > sharedViewModel.actualWeight -> {
                    val dialog = AddProductDialog()
                    dialog.show(parentFragmentManager, "AddToCartDialog")
                    lifecycleScope.launch(Dispatchers.Default) {
                        while (!areEqual) {
                            areEqual = sharedViewModel.checkActualWeightWithExpectedWeight(sharedViewModel.actualWeight, sharedViewModel.expectedWeight.value!!)
                        }
                        withContext(Dispatchers.Main){
                            dialog.dismiss()
                            val action = CartFragmentDirections.actionCartFragmentToBillFragment()
                            view.findNavController().navigate(action)
                        }
                    }
                }
                sharedViewModel.expectedWeight.value!! < sharedViewModel.actualWeight -> {
                    val dialog = WeightErrorDialog()
                    dialog.show(parentFragmentManager, "WeightErrorDialog")
                    lifecycleScope.launch(Dispatchers.Default) {
                        while (!areEqual) {
                            areEqual = sharedViewModel.checkActualWeightWithExpectedWeight(sharedViewModel.actualWeight, sharedViewModel.expectedWeight.value!!)
                        }
                        withContext(Dispatchers.Main){
                            dialog.dismiss()
                            val action = CartFragmentDirections.actionCartFragmentToBillFragment()
                            view.findNavController().navigate(action)
                        }
                    }
                }
            }
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                try {
                    val dialog = query?.let { SearchDialog(it) }
                    dialog?.show(parentFragmentManager,"SearchDialog")
                }catch (e: Exception){
                    Log.d("productCode",e.toString())
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.marketBtn.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToMapFragment2()
            view.findNavController().navigate(action)
        }
    }


    override fun onStop() {
        super.onStop()
        sharedViewModel.reInitializeBarcodeReceiver()
    }

    private fun addingProductProcess(receivedBarcode: String){
        val dialog = LoadingDialog()
        dialog.show(parentFragmentManager, "LoadingDialog")

        lifecycleScope.launch(Dispatchers.IO) {
            val product = async { sharedViewModel.getProduct(receivedBarcode) }
            val waitedProduct = product.await()
            if (waitedProduct != null){
                ConfirmationDialog(waitedProduct, sharedViewModel.list).show(parentFragmentManager, "ConfirmationDialog")
                    .also { dialog.dismiss() }
            }else{
                dialog.dismiss()
            }
        }
    }

    override fun deleteProduct(product: ProductModel) {
        val dialog = RemoveDialog(product)
        dialog.show(parentFragmentManager,"RemoveDialog")
    }
}