package com.interview.fashiondays.data.restmanager

import android.util.Log
import com.interview.fashiondays.data.model.response.ProductListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsRepository(var service: ProductsService?, var responseCallback: ResponseCallback?) {
    private val TAG = "ProductsRepository"
    private var productListCall: Call<ProductListResponse>? = null

    fun getProductList() {
        productListCall = service?.getProducts()
        responseCallback?.onLoading()
        sendRequest()
    }

    private fun sendRequest() {
        productListCall?.enqueue(object : Callback<ProductListResponse> {
            override fun onResponse(call: Call<ProductListResponse>, response: Response<ProductListResponse>) {
                handleSuccessCallback(response)
            }

            override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {
                handleFailureCallback()
            }
        })
    }

    private fun handleFailureCallback() {
        Log.e(TAG, "error trying to get the products list")
        handleErrorResponse()
    }

    private fun handleSuccessCallback(response: Response<ProductListResponse>) {
        if (response.isSuccessful) {
            handleSuccessfulResponse(response)
        } else {
            handleErrorResponse()
        }
    }

    private fun handleSuccessfulResponse(response: Response<ProductListResponse>) {
        val body = response.body()?.products?.filterNotNull()
        if (body.isNullOrEmpty()) {
            responseCallback?.onEmptyResponse()
        } else {
            responseCallback?.onSuccess(body)
        }
    }

    private fun handleErrorResponse() {
        responseCallback?.onError()
    }

    fun cancel() {
        if (productListCall != null) {
            productListCall!!.cancel()
        }
    }
}