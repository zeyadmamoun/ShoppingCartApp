package com.example.shoppingcart.ui

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcart.models.Item
import com.example.shoppingcart.models.PaymentStatus
import com.example.shoppingcart.models.ProductModel
import com.example.shoppingcart.models.ResponseBody
import com.example.shoppingcart.networkAndConnections.*
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import retrofit2.Call

class SharedViewModel : ViewModel(){
    // The internal MutableLiveData that stores the list of the products.
    private val _products = MutableLiveData<List<ProductModel>>()
    // The external immutable LiveData for the List of Products.
    val products : LiveData<List<ProductModel>> = _products
    // temporary list to assign the _products of type mutableLiveData.
    val list = mutableListOf<ProductModel>()
    // the total price of the products.
    private val _totalPrice = MutableLiveData(0.0)
    // The external immutable LiveData for the total price.
    val price : LiveData<Double> = _totalPrice
    // Instance of our bluetooth class that hold all bluetooth function.
    var bluetooth: BluetoothThread? = null

    private var _receivedBarcode = MutableLiveData<String>()
    val receivedBarcode: LiveData<String> = _receivedBarcode

    var actualWeight: Int = 0
    private var _expectedWeight = MutableLiveData(0)
    val expectedWeight: LiveData<Int> = _expectedWeight

    val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                BARCODE_PACKET -> {
                    val receivedBarcode = msg.obj
                    Log.d("bluetoothTag", "Barcode : $receivedBarcode")
                    if (receivedBarcode != "")
                        _receivedBarcode.value = receivedBarcode.toString()

                }
                WEIGHT_PACKET -> {
                    val receivedWeight = msg.obj.toString()
                    if (receivedWeight != "" && receivedWeight.length <= 5){
                        Log.d("bluetoothTag", "Weight : $receivedWeight")
                        val receivedWeightToInt = receivedWeight.toInt()
                        checkActualWeightErrorPercentage(receivedWeightToInt)
                    }
                }
            }
        }
    }

    suspend fun getProduct(barcode: String) :  ProductModel?{
        var product:  ProductModel? = null
        try {
            product = ProductsApi.retrofitServices.getProduct(barcode)
        } catch (e: Exception) {
            println(e)
            Log.d("productCode",e.toString())
        }
        return product
    }

    fun postBillItems(method: String): ResponseBody? {
        val items = mutableListOf<Item>()
        _products.value?.forEach { product ->
            val item = Item(
                product.id,
                product.productName,
                product.productPrice.toInt(),
                product.productUnit
            )
            items.add(item)
        }

        val billMap = mapOf<Any, Any>(
            "method" to method,
            "total" to price.value!!,
            "cart" to "A0001",
            "items" to items
        )
        return try {
            val call : Call<ResponseBody> = BillPostApi.retrofitServices.postBill(billMap)
            call.execute().body()
        } catch (e: Exception) {
            Log.d("productCode", e.toString())
            return null
        }
    }

    suspend fun searchProducts(query: String) : List<ProductModel>?{
        var posts: List<ProductModel>? =null
        try {
            posts = SearchApi.retrofitServices.searchProducts(query)
        }catch (e: Exception){
            Log.d("productCode",e.toString())
        }
        return posts
    }

    suspend fun getStatus(id: Int) : PaymentStatus? {
        var status: PaymentStatus? = null
        try {
            status = ConfirmPayment.retrofitServices.getStatus(id)
        }catch (e: Exception){
            Log.d("productCode",e.toString())
        }
        return status
    }

    fun checkIfProductExist(product: ProductModel, list: MutableList<ProductModel>){
        var isExist = false
        if (list.isEmpty()){
            list.add(product)
        }else{
            val iterator = list.iterator()
            while (iterator.hasNext()){
                val item = iterator.next()
                if (item == product){
                    isExist = true
                    item.productUnit++
                }
            }
            if (!isExist)
                list.add(product)
        }
        assignProductsList()
    }

    fun getTotalPrice(){
        val total = ArrayList<Double>()
        _products.value?.forEach { product ->
            if (product.productName.length > 40)
                product.productName = product.productName.substring(0,39)
            product.calculateProductPrice()
            total.add(product.totalPrice)
        }
        _totalPrice.value =  total.sum()
    }

    fun generateQR(imageView: ImageView,url: String){
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(url,BarcodeFormat.QR_CODE, 200,200)
            imageView.setImageBitmap(bitmap)
        }catch (e: Exception){
            println(e)
        }
    }

    fun assignProductsList(){
        _products.value = list
    }
    fun reInitializeBarcodeReceiver(){
        _receivedBarcode.value = " "
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //weight logic functions.
    fun checkActualWeightErrorPercentage(receivedWeight: Int) {
        if (receivedWeight/1000 > 20){
            return
        }else if (receivedWeight in (actualWeight - 10..actualWeight + 10)) {
            return
        } else {
            actualWeight = receivedWeight
        }
        Log.d("bluetoothTag", actualWeight.toString())
    }

    fun checkActualWeightWithExpectedWeight(actualWeight: Int,expectedWeight: Int): Boolean{
        return actualWeight in (expectedWeight - 120..expectedWeight + 120)
    }

    fun increaseExpectedWeight(newWeight: Int){
        _expectedWeight.value = _expectedWeight.value?.plus(newWeight)
    }

    fun decreaseExpectedWeight(removedWeight: Int){
        _expectedWeight.value = _expectedWeight.value?.minus(removedWeight)
    }
}
