@file:Suppress("DEPRECATION")

package com.ribeiro.gabriel.compras.application.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.ribeiro.gabriel.compras.R
import com.ribeiro.gabriel.compras.domain.entities.Product

import com.ribeiro.gabriel.compras.domain.factories.ProductFactory
import com.ribeiro.gabriel.compras.domain.services.ProductService
import com.ribeiro.gabriel.compras.domain.transportObject.RestRequestResult
import com.ribeiro.gabriel.compras.domain.types.ResponseStatus
import com.ribeiro.gabriel.compras.exceptions.InvalidDescriptionException
import com.ribeiro.gabriel.compras.exceptions.InvalidNameException
import com.ribeiro.gabriel.compras.exceptions.InvalidPriceException
import kotlinx.android.synthetic.main.fragment_register.*

@Suppress("DEPRECATION")
class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        register_product.setOnClickListener {
            try {
                val newProduct = ProductFactory.createNewProduct(
                        name = name_new_item.text.toString(),
                        price = price_new_item.text.toString().toFloat(),
                        description = description_new_item.text.toString()
                )

                RegisterNewProductTask(this.activity).execute(newProduct)

            } catch (e: InvalidNameException){
                e.printStackTrace()
                Log.e(TAG, "Dumb user tapped wrong name")
                createDialog(getString(R.string.fail_to_register_title), getString(R.string.invalid_name_message))
            } catch (e: InvalidPriceException) {
                e.printStackTrace()
                Log.e(TAG, "Dumb user tapped wrong price")
                createDialog(getString(R.string.fail_to_register_title), getString(R.string.invalid_price_message))
            } catch (e: InvalidDescriptionException) {
                e.printStackTrace()
                Log.e(TAG, "Dumb user tapped wrong description")
                createDialog(getString(R.string.fail_to_register_title), getString(R.string.invalid_description_message))
            }
        }
    }

    private fun createDialog(title: String, message: String){
        val alertDialogBuilder = AlertDialog.Builder(this.activity)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK", { _, _ -> })
        alertDialogBuilder.create().show()
    }
    @SuppressLint("StaticFieldLeak")
    private class RegisterNewProductTask(var activityContext: Activity) : AsyncTask<Product, Void, RestRequestResult>() {
        var progressDialog: ProgressDialog? = null
        var alertDialogBuilder: AlertDialog.Builder? = null

        init {
            progressDialog = ProgressDialog(activityContext)
            alertDialogBuilder = AlertDialog.Builder(activityContext)
        }

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog?.setTitle(activityContext.getString(R.string.please_wait_message))
            progressDialog?.setMessage(activityContext.getString(R.string.registering_new_product_message))
            progressDialog?.setCancelable(false)
            progressDialog?.show()
        }

        override fun doInBackground(vararg product: Product): RestRequestResult {
            return ProductService.registerNewProduct(product[0])
        }

        override fun onPostExecute(requestResult: RestRequestResult) {
            super.onPostExecute(requestResult)
            try {
                progressDialog?.dismiss()
            }catch (e: Exception){
                e.printStackTrace()
                Log.e(TAG, "Error dismissing progress dialog")
            }

            if (requestResult.status == ResponseStatus.SUCCESS) {
                createDialog(activityContext.getString(R.string.success_title), activityContext.getString(R.string.new_item_registered_message))
                resetFields()
            } else {
                createDialog(activityContext.getString(R.string.fail_to_register_title), requestResult.result!! as String)
            }
        }

        private fun createDialog(title: String, message: String){
            alertDialogBuilder?.setTitle(title)
            alertDialogBuilder?.setMessage(message)
            alertDialogBuilder?.setPositiveButton("OK", { _, _ -> })
            alertDialogBuilder?.create()?.show()
        }

        private fun resetFields(){
            activityContext.findViewById<EditText>(R.id.name_new_item).setText("")
            activityContext.findViewById<EditText>(R.id.price_new_item).setText("")
            activityContext.findViewById<EditText>(R.id.description_new_item).setText("")
        }
    }
}
