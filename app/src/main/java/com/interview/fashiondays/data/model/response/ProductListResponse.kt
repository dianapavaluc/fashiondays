package com.interview.fashiondays.data.model.response

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("products") val products: List<Product?>?,
)

data class Product(
    @SerializedName("product_id") val productId: Long?,
    @SerializedName("product_brand") val productBrand: String?,
    @SerializedName("product_name") val productName: String?,
    @SerializedName("product_images") val productImages: Image?
)

data class Image(
    @SerializedName("thumb") val thumb: List<String>?,
    @SerializedName("zoom") val zoom: List<String>?
)