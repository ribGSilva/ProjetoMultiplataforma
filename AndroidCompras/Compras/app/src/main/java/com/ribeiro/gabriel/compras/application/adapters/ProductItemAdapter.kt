package com.ribeiro.gabriel.compras.application.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ribeiro.gabriel.compras.R
import com.ribeiro.gabriel.compras.application.contracts.ProductRemover
import com.ribeiro.gabriel.compras.domain.entities.Product
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductItemAdapter(private val items: List<Product>,
                         private val context: Context,
                         private val productRemover: ProductRemover) : RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProductItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false)
        return ProductItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.price.text = "R$${item.price}"
        holder.description.text = item.description
        holder.removeButton.setOnClickListener {
            removeButton ->  productRemover.removeProductAtPosition(removeButton.tag.toString().toInt())
        }
    }

    class ProductItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.product_list_item_name!!
        val price = itemView.product_list_item_price!!
        val description = itemView.product_list_item_description!!
        val removeButton = itemView.product_list_item_remove!!
    }
}