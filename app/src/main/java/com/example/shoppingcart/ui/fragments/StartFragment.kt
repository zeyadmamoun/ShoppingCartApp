package com.example.shoppingcart.ui.fragments

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context.BLUETOOTH_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.shoppingcart.databinding.FragmentStartBinding
import com.example.shoppingcart.networkAndConnections.BluetoothThread
import com.example.shoppingcart.ui.SharedViewModel

class StartFragment : Fragment() {

    private var _binding : FragmentStartBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bluetoothManager = activity?.applicationContext?.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter : BluetoothAdapter? = bluetoothManager.adapter
        if (bluetoothAdapter != null){
            sharedViewModel.bluetooth = BluetoothThread(requireActivity().applicationContext, bluetoothAdapter,sharedViewModel.handler)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentStartBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.bluetooth?.let { it ->
            it.enableBluetooth()
            it.ConnectThread().start()
        }
        binding.button.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToCartFragment()
            view.findNavController().navigate(action)
        }
    }
}