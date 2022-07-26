package com.example.shoppingcart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.databinding.SearchCardBinding
import com.example.shoppingcart.models.ProductModel

class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.ResultViewHolder>() {

    private var productsList = mutableListOf<ProductModel>()

    class ResultViewHolder(private var binding: SearchCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel : ProductModel) {
            binding.product = productModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultViewHolder {
        return ResultViewHolder(SearchCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val product = productsList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun setList(list: List<ProductModel>?){
        list?.let {
            this.productsList = list as MutableList<ProductModel>
            notifyDataSetChanged()
        }
    }
}