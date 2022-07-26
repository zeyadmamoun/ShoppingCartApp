package com.example.shoppingcart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.databinding.BillItemBinding
import com.example.shoppingcart.models.ProductModel

class BillAdapter : ListAdapter<ProductModel,BillAdapter.BillItemViewHolder>(BillCallback){
    class BillItemViewHolder(private var binding: BillItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel: ProductModel?){
            binding.product = productModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BillItemViewHolder {
        return BillItemViewHolder(BillItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BillItemViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    companion object BillCallback : DiffUtil.ItemCallback<ProductModel>() {

        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return when{
                oldItem.productName == newItem.productName ->{
                    true
                }
                oldItem.productImage == newItem.productImage ->{
                    true
                }
                oldItem.productPrice == newItem.productPrice ->{
                    true
                }
                else -> false
            }
        }
    }
}