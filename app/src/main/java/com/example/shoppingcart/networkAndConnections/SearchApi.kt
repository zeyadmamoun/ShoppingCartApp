package com.example.shoppingcart.networkAndConnections

import com.example.shoppingcart.models.ProductModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//
//https://smart-cart-server.herokuapp.com/store/
//private const val BASE_URL = "http://192.168.43.29:5001/"
private const val BASE_URL = "http://192.168.239.25:8000/store/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val searchClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(50,TimeUnit.SECONDS)
    .readTimeout(50,TimeUnit.SECONDS).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(searchClient)
    .build()

interface SearchApiServices{
    //search
//    @GET("products")
//    suspend fun searchProducts(@Query("word")searchQuery : String) : List<ProductModel>
    @GET("search")
    suspend fun searchProducts(@Query("q")searchQuery : String) : List<ProductModel>
}

object SearchApi{
    val retrofitServices : SearchApiServices by lazy {
        retrofit.create(SearchApiServices ::class.java)
    }
}