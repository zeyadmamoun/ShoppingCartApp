package com.example.shoppingcart.ui.fragments

import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.FragmentMapBinding
import com.example.shoppingcart.ui.canvas.CustomMap

class MapFragment : Fragment() {
    private var _binding : FragmentMapBinding? = null
    private val binding get() = _binding!!
//    private lateinit var customMap : CustomMap
    private lateinit var attr: AttributeSet

    override fun onResume() {
        super.onResume()
        val sections = resources.getStringArray(R.array.sections)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,sections)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map,container,false)

        attr = object : AttributeSet {
            override fun getAttributeCount(): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeName(p0: Int): String {
                TODO("Not yet implemented")
            }

            override fun getAttributeValue(p0: Int): String {
                TODO("Not yet implemented")
            }

            override fun getAttributeValue(p0: String?, p1: String?): String {
                TODO("Not yet implemented")
            }

            override fun getPositionDescription(): String {
                TODO("Not yet implemented")
            }

            override fun getAttributeNameResource(p0: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeListValue(
                p0: String?,
                p1: String?,
                p2: Array<out String>?,
                p3: Int
            ): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeListValue(p0: Int, p1: Array<out String>?, p2: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeBooleanValue(p0: String?, p1: String?, p2: Boolean): Boolean {
                TODO("Not yet implemented")
            }

            override fun getAttributeBooleanValue(p0: Int, p1: Boolean): Boolean {
                TODO("Not yet implemented")
            }

            override fun getAttributeResourceValue(p0: String?, p1: String?, p2: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeResourceValue(p0: Int, p1: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeIntValue(p0: String?, p1: String?, p2: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeIntValue(p0: Int, p1: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeUnsignedIntValue(p0: String?, p1: String?, p2: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeUnsignedIntValue(p0: Int, p1: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getAttributeFloatValue(p0: String?, p1: String?, p2: Float): Float {
                TODO("Not yet implemented")
            }

            override fun getAttributeFloatValue(p0: Int, p1: Float): Float {
                TODO("Not yet implemented")
            }

            override fun getIdAttribute(): String {
                TODO("Not yet implemented")
            }

            override fun getClassAttribute(): String {
                TODO("Not yet implemented")
            }

            override fun getIdAttributeResourceValue(p0: Int): Int {
                TODO("Not yet implemented")
            }

            override fun getStyleAttribute(): Int {
                TODO("Not yet implemented")
            }

        }
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var current = ""
        var destination = ""


        binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val selection = adapterView.getItemAtPosition(i).toString()
            if (selection != current ){
                current = selection
            }
            Log.d("productCode",adapterView.getItemAtPosition(i).toString())
        }

        binding.autoCompleteTextView2.setOnItemClickListener { adapterView, view, i, l ->
            val selection = adapterView.getItemAtPosition(i).toString()
            if (selection != destination){
                destination = selection
            }
            Log.d("productCode",adapterView.getItemAtPosition(i).toString())
        }

        binding.pathPtn.setOnClickListener {
            binding.customMapView.setValues(current,destination)
        }
    }
}