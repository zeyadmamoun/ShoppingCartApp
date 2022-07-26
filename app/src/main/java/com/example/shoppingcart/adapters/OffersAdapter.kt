package com.example.shoppingcart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.databinding.OffersCardBinding
import com.example.shoppingcart.models.ProductModel

class OffersAdapter : ListAdapter<ProductModel,OffersAdapter.OffersViewHolder>(OfferCallback){

    class OffersViewHolder(private var binding: OffersCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel: ProductModel?){
            binding.product = productModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OffersViewHolder {
        return OffersViewHolder(OffersCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    companion object OfferCallback : DiffUtil.ItemCallback<ProductModel>() {

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