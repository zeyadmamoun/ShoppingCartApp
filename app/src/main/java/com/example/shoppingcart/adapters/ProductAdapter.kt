package com.example.shoppingcart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.ProductsCardBinding
import com.example.shoppingcart.models.ProductModel

class ProductAdapter(deleteListener: DeleteListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productsList = mutableListOf<ProductModel>()
    private var deleteListener: DeleteListener

    init {
        this.deleteListener = deleteListener
    }

    class ProductViewHolder(private var binding: ProductsCardBinding,private  var deleteListener: DeleteListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel : ProductModel) {
            binding.product = productModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(ProductsCardBinding.inflate(LayoutInflater.from(parent.context),parent,false),this.deleteListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productsList[position]
        holder.bind(product)
        holder.itemView.findViewById<ImageView>(R.id.remove_btn).setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                deleteListener.deleteProduct(productsList[position])
            }
        }
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