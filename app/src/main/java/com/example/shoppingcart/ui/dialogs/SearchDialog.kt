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
import com.example.shoppingcart.R
import com.example.shoppingcart.adapters.SearchResultsAdapter
import com.example.shoppingcart.databinding.SearchResultsDialogBinding
import com.example.shoppingcart.ui.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchDialog(private var query: String) : DialogFragment() {
    private var _binding: SearchResultsDialogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private var searchAdapter = SearchResultsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialoge_background_corners)
        _binding = DataBindingUtil.inflate(inflater, R.layout.search_results_dialog,container,false)
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

        binding.searchResultsRecyclerview.adapter = searchAdapter

        try {
            lifecycleScope.launch(Dispatchers.IO) {
                val posts = async { sharedViewModel.searchProducts(query) }
                val waitedPosts = posts.await()
                Log.d("productCode", waitedPosts.toString())
                if (waitedPosts!!.isNotEmpty()){
                    withContext(Dispatchers.Main){
                        searchAdapter.setList(waitedPosts)
                    }
                }else{
                    Log.d("productCode", "Empty List")
                }
            }
        }catch (e: Exception){
            Log.d("productCode", e.toString())
        }

        binding.dismissBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }
}