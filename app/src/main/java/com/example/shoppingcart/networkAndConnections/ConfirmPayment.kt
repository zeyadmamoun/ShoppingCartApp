package com.example.shoppingcart.networkAndConnections

import com.example.shoppingcart.models.PaymentStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit
//https://smart-cart-payment.herokuapp.com/
//http://192.168.1.2:8000/payment/
private const val BASE_URL = "http://192.168.239.25:8000/payment/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val paymentClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(50, TimeUnit.SECONDS)
    .readTimeout(50, TimeUnit.SECONDS).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(paymentClient)
    .build()

interface PaymentStatusApi{
    @GET("confirm_payment/{id}")
    suspend fun getStatus(@Path("id")id : Int) : PaymentStatus
}

object ConfirmPayment{
    val retrofitServices : PaymentStatusApi by lazy {
        retrofit.create(PaymentStatusApi ::class.java)
    }
}