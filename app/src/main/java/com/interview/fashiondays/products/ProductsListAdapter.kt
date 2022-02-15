package com.interview.fashiondays.products

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.interview.fashiondays.R
import com.interview.fashiondays.data.model.response.Product
import com.interview.fashiondays.databinding.ItemProductBinding

class ProductsListAdapter : ListAdapter<Product, ProductsListAdapter.ProductHolder>(Companion) {

    var onProductDeleted: ((Product) -> Unit)? = null

    companion object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem.productId == newItem.productId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = getItem(position)
        holder.binding.apply {

            Glide.with(imageViewProduct.context)
                .load(product.productImages?.zoom?.get(0)?.replace(" ", ""))
                .centerCrop()
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_error)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        imageViewProduct.background = errorDrawable
                    }

                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?,
                    ) {
                        imageViewProduct.background = resource
                    }
                })

            textViewBrand.text = product.productBrand
            textViewProductName.text = product.productName

            imageViewProduct.setOnLongClickListener {
                onProductDeleted?.invoke(product)
                true
            }
        }
    }

    fun clearCurrentList() {
        submitList(emptyList())
    }

    class ProductHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
}