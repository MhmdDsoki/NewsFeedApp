package com.example.newsfeedapp.common

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Activity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}


fun View.show() {
    this.visibility = View.VISIBLE
}


fun View.gone() {
    this.visibility = View.GONE
}


fun Fragment.showMsg(msg: String) {
    view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show() }
}


fun Fragment.showDialog(
    message: String?, posActionName: String?,
    onPosClick: DialogInterface.OnClickListener?,
    negativeText: String?,
    onNegativeClick: DialogInterface.OnClickListener?,
    isCancelable: Boolean
) {

    val builder = AlertDialog.Builder(requireContext())
    builder.setMessage(message)
    builder.setPositiveButton(posActionName, onPosClick)
    builder.setNegativeButton(negativeText, onNegativeClick)
    builder.setCancelable(isCancelable)

    val dialog: AlertDialog = builder.create()
    dialog.show()
}













