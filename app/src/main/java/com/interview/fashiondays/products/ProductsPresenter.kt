package com.interview.fashiondays.products

import com.interview.fashiondays.base.BasePresenter
import com.interview.fashiondays.data.model.response.Product
import com.interview.fashiondays.data.restmanager.ProductsRepository
import com.interview.fashiondays.data.restmanager.ProductsService

class ProductsPresenter: BasePresenter<ProductsMVP.View>(), ProductsMVP.Presenter {

    private val repository: ProductsRepository by lazy {
        ProductsRepository(ProductsService.create(), this)
    }

    override fun getProducts() {
        view?.showLoader()
        repository.getProductList()
    }

    override fun onDestroy() {
        repository.cancel()
    }

    override fun cancelDataCall() {
        repository.cancel()
    }

    override fun onSuccess(products: List<Product>) {
        view?.hideLoader()
        view?.updateProductsList(products.toMutableList())
    }

    override fun onError() {
        view?.hideLoader()
        view?.showError()
    }

    override fun onLoading() {
        view?.showLoader()
    }

    override fun onEmptyResponse() {
        view?.hideLoader()
        view?.showEmptyList()
    }
}