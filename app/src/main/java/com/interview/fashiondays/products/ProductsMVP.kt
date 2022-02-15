package com.interview.fashiondays.products

import com.interview.fashiondays.base.BaseMvp
import com.interview.fashiondays.data.model.response.Product

class ProductsMVP {
    interface View: BaseMvp.View  {
        fun updateProductsList(products: MutableList<Product>)
        fun showError()
        fun showEmptyList()
    }

    interface Presenter: BaseMvp.Presenter  {
        fun getProducts()
    }
}