package com.example.newsfeedapp.common

import android.app.Activity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsfeedapp.R
import com.google.android.material.snackbar.Snackbar


fun Activity.showToast(msg : String ){
    Toast.makeText( this ,msg, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(msg: String) {
    Toast.makeText( requireContext() ,msg, Toast.LENGTH_LONG).show()
}


fun View.show(){
    this.visibility = View.VISIBLE
}


fun View.gone(){
    this.visibility = View.GONE
}

fun MenuItem.gone(){
    this.isVisible = false
}

fun MenuItem.show(){
    this.isVisible=true
}

 fun Fragment.showMsg(msg: String) {
     view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show() }
 }












