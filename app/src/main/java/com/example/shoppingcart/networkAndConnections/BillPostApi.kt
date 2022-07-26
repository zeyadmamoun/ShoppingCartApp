package com.example.shoppingcart.networkAndConnections

import com.example.shoppingcart.models.ResponseBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

//https://smart-cart-payment.herokuapp.com/
//http://192.168.1.2:8000/payment/
private const val BASE_URL = "http://192.168.239.25:8000/payment/"

val postClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(200, TimeUnit.SECONDS)
    .readTimeout(200, TimeUnit.SECONDS).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(postClient)
    .build()

interface BillApiServices{
    @POST("pay/")
    @JvmSuppressWildcards
    fun postBill(@Body bill: Map<Any,Any>) : Call<ResponseBody>
}

object BillPostApi{
    val retrofitServices : BillApiServices by lazy {
        retrofit.create(BillApiServices ::class.java)
    }
}

