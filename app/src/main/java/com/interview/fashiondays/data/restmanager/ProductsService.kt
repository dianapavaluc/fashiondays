package com.interview.fashiondays.data.restmanager

import com.google.gson.GsonBuilder
import com.interview.fashiondays.BuildConfig
import com.interview.fashiondays.data.model.response.ProductListResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ProductsService {

    @GET("products")
    fun getProducts(): Call<ProductListResponse>

    companion object {
        fun create(): ProductsService {

            val httpClient = CustomHttpClient().provideHttpClientDefaultBuilder()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(ProductsService::class.java)
        }
    }
}