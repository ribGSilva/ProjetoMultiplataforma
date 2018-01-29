@file:Suppress("DEPRECATION")

package com.ribeiro.gabriel.compras.application.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ribeiro.gabriel.compras.R
import com.ribeiro.gabriel.compras.application.adapters.ProductItemAdapter
import com.ribeiro.gabriel.compras.application.contracts.ProductRecyclerViewUpdater
import com.ribeiro.gabriel.compras.application.contracts.ProductRemover
import com.ribeiro.gabriel.compras.domain.entities.Product
import com.ribeiro.gabriel.compras.domain.services.ProductService
import com.ribeiro.gabriel.compras.domain.transportObject.RestRequestResult
import com.ribeiro.gabriel.compras.domain.types.ResponseStatus
import kotlinx.android.synthetic.main.fragment_registered.*

@Suppress("DEPRECATION")
class RegisteredFragment : Fragment(), ProductRemover, ProductRecyclerViewUpdater {

    private var productList = ArrayList<Product>()
    private var productItemAdapter: ProductItemAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_registered, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getListFromRest()
    }

    override fun onResume() {
        super.onResume()
        getListFromRest()
    }

    private fun getListFromRest() {
        GetProductsTask(this.activity, this).execute()
    }

    override fun updateProductRecyclerView(newList: ArrayList<Product>) {
        productItemAdapter = ProductItemAdapter(productList, this.activity, this)
        productRecyclerView.adapter = productItemAdapter
        productItemAdapter!!.notifyDataSetChanged()
    }

    override fun removeProductAtPosition(position: Int) {
        RemoveProductTask(this.activity).execute(position)
    }

    @SuppressLint("StaticFieldLeak")
    private class GetProductsTask(
            var activityContext: Activity,
            var productRecyclerViewUpdater: ProductRecyclerViewUpdater
    ): AsyncTask<Void, Void, RestRequestResult>() {

        var alertDialogBuilder: AlertDialog.Builder? = null

        init {
            alertDialogBuilder = AlertDialog.Builder(activityContext)
        }

        override fun doInBackground(vararg params: Void?): RestRequestResult {
            return ProductService.getProductList()
        }

        override fun onPostExecute(requestResult: RestRequestResult) {
            super.onPostExecute(requestResult)

            if (requestResult.status == ResponseStatus.SUCCESS) {
                val listReturned = requestResult.result!! as ArrayList<Product>
                productRecyclerViewUpdater.updateProductRecyclerView(newList = listReturned)
            } else {
                Snackbar.make(activityContext.findViewById(R.id.coordinatorLayout), R.string.fail_to_remove_title, Snackbar.LENGTH_LONG)
                Log.e(TAG, "Error at getting products: ${requestResult.result!! as String} ")
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RemoveProductTask(var activityContext: Activity) : AsyncTask<Int, Void, RestRequestResult>() {

        var progressDialog: ProgressDialog? = null
        var alertDialogBuilder: AlertDialog.Builder? = null

        init {
            progressDialog = ProgressDialog(activityContext)
            alertDialogBuilder = AlertDialog.Builder(activityContext)
        }

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog?.setTitle(activityContext.getString(R.string.please_wait_message))
            progressDialog?.setMessage(activityContext.getString(R.string.removing_product_message))
            progressDialog?.setCancelable(false)
            progressDialog?.show()
        }

        override fun doInBackground(vararg params: Int?): RestRequestResult {
            return ProductService.removeProductAtPosition(params[0]!!)
        }

        override fun onPostExecute(requestResult: RestRequestResult) {
            super.onPostExecute(requestResult)
            try {
                progressDialog?.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(ContentValues.TAG, "Error dismissing progress dialog")
            }

            if (requestResult.status == ResponseStatus.SUCCESS) {
                createDialog(activityContext.getString(R.string.success_title), activityContext.getString(R.string.item_removed_message))
            } else {
                createDialog(activityContext.getString(R.string.fail_to_remove_title), requestResult.result!! as String)
            }
        }

        private fun createDialog(title: String, message: String) {
            alertDialogBuilder?.setTitle(title)
            alertDialogBuilder?.setMessage(message)
            alertDialogBuilder?.setPositiveButton("OK", { _, _ -> })
            alertDialogBuilder?.create()?.show()
        }

    }
}
