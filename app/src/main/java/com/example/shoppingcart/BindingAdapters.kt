package com.example.shoppingcart

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shoppingcart.adapters.BillAdapter
import com.example.shoppingcart.adapters.OffersAdapter
import com.example.shoppingcart.models.ProductModel

@BindingAdapter("imageUrl")
fun bindImage(imgView : ImageView, imgUrl : String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
        imgView.load(imgUri){}
    }
}

@BindingAdapter("offerData")
fun bindOffersList(recyclerView: RecyclerView, data: List<ProductModel>?){
    val adapter = recyclerView.adapter as OffersAdapter
    adapter.submitList(data)
}

@BindingAdapter("billData")
fun bindBillList(recyclerView: RecyclerView, data: List<ProductModel>?){
    val adapter = recyclerView.adapter as BillAdapter
    adapter.submitList(data)
}
