package com.example.cvault;

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AlertDialog

object ProgressDialogUtil {

    private var progressDialog: ProgressDialog? = null

    fun showAlert(message: String?, title: String?, context: Context?) {
        val localBuilder = AlertDialog.Builder(
            context!!
        )
        localBuilder.setTitle(title)
        localBuilder.setMessage(message)
        localBuilder.setCancelable(true)
        localBuilder.setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
        val alert = localBuilder.create()
        alert.show()
    }

    fun showLoadingDialog(activity: Activity?, msg: String?): ProgressDialog? {
        if (progressDialog != null && progressDialog!!.isShowing) {
            return progressDialog
        }
        progressDialog = ProgressDialog.show(activity, null, msg, false, false)
        return progressDialog
    }

    fun dismissLoadingDialog(activity: Activity?) {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isLoadingDialogShowing(): Boolean {
        return progressDialog != null && progressDialog!!.isShowing
    }

    fun updateLoadingDialog(msgString: String?) {
        if (progressDialog != null && progressDialog!!.isShowing)
            progressDialog!!.setMessage(msgString)
    }
}
