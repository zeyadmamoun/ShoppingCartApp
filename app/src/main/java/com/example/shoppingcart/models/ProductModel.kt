package com.example.shoppingcart.models

import com.squareup.moshi.Json

data class ProductModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") var productName: String,
    @Json(name = "img") val productImage: String,
    @Json(name = "price") var productPrice: String,
    @Json(name = "category") var productSection: String,
    var productUnit: Int = 1,
    @Json(name = "weight") var productWeight: Int = 0,
    var totalPrice: Double = 0.0
){
    fun calculateProductPrice() {
        val sum = productPrice.toDouble() * productUnit
        totalPrice = sum
    }

    override fun equals(other: Any?): Boolean {
        other as ProductModel
        return id == other.id && productImage == other.productImage
    }

    fun printProductDetails(){
        print("{ product ID : $id ,")
        print("product name : $productName ,")
        print("product price : $productPrice ,")
        print("product Units : $productUnit ,")
        print("product weight : $productWeight ,")
        println("total price : $totalPrice }")
    }
}