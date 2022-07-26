package com.example.shoppingcart.adapters

import com.example.shoppingcart.models.ProductModel

interface DeleteListener {
    fun deleteProduct(product: ProductModel)
}