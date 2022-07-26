package com.example.shoppingcart.networkAndConnections

import com.example.shoppingcart.models.ProductModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

//smart-cart-server.herokuapp.com
// nady: http://192.168.43.29:7175/
//private const val BASE_URL = "http://192.168.43.29:5001/"
private const val BASE_URL = "http://192.168.239.25:8000/store/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val client: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(50,TimeUnit.SECONDS)
    .readTimeout(50,TimeUnit.SECONDS).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface ProductsApiServices{
    @GET("products/{id}")
    suspend fun getProduct(@Path("id")id : String) : ProductModel
//    @GET("products/{id}")
//    suspend fun getProduct(@Path("id")id : String) : ProductModel
}

object ProductsApi{
    val retrofitServices : ProductsApiServices by lazy {
        retrofit.create(ProductsApiServices ::class.java)
    }
}