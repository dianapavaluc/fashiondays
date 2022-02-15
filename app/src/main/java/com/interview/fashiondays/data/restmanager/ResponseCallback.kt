package com.interview.fashiondays.data.restmanager

import com.interview.fashiondays.data.model.response.Product

interface ResponseCallback {
    fun onSuccess(products: List<Product>)
    fun onError()
    fun onLoading()
    fun onEmptyResponse()
}
