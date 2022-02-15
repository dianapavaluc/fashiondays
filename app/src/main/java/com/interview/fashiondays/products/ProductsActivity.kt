package com.interview.fashiondays.products

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.interview.fashiondays.R
import com.interview.fashiondays.base.BaseActivity
import com.interview.fashiondays.data.model.response.Product
import com.interview.fashiondays.databinding.ActivityProductsBinding

class ProductsActivity: BaseActivity<ActivityProductsBinding>(ActivityProductsBinding::inflate), ProductsMVP.View {

    private val presenter: ProductsPresenter by lazy {
        ProductsPresenter()
    }
    private lateinit var productAdapter: ProductsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        presenter.getProducts()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun initViews(){
        productAdapter = ProductsListAdapter()
        binding.layoutList.recycler.adapter = productAdapter

        productAdapter.onProductDeleted = {
            val updatedList = productAdapter.currentList.toMutableList()
            updatedList.remove(it)
            if (updatedList.isEmpty()) {
                productAdapter.clearCurrentList()
                showEmptyList()
            } else productAdapter.submitList(updatedList)
            Toast.makeText(this, getText(R.string.item_deleted), Toast.LENGTH_LONG).show()

        }
        binding.layoutList.swipeLayout.setOnRefreshListener {
            productAdapter.clearCurrentList()
            presenter.getProducts()
            binding.layoutList.swipeLayout.isRefreshing = false
        }

    }

    override fun updateProductsList(products: MutableList<Product>) {
        productAdapter.submitList(products)
        binding.layoutList.recycler.visibility = View.VISIBLE
        binding.layoutEmpty.emptyListTextView.visibility  = View.GONE
        binding.layoutError.textViewError.visibility  = View.GONE

    }

    override fun showLoader() {
        binding.layoutLoading.root.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        binding.layoutLoading.root.visibility = View.GONE
    }

    override fun showError() {
        binding.layoutList.recycler.visibility = View.GONE
        binding.layoutEmpty.emptyListTextView.visibility  = View.GONE
        binding.layoutError.textViewError.visibility  = View.VISIBLE
    }

    override fun showEmptyList() {
        binding.layoutList.recycler.visibility = View.GONE
        binding.layoutEmpty.emptyListTextView.visibility  = View.VISIBLE
        binding.layoutError.textViewError.visibility  = View.GONE
    }

}
