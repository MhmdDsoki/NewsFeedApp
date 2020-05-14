package com.example.newsfeedapp.common

import android.app.Activity
import android.view.MenuItem
import android.view.View
import android.widget.Toast


fun Activity.showToast(msg : String ){
    Toast.makeText( this ,msg, Toast.LENGTH_LONG).show()
}


fun View.show(){
    this.visibility = View.VISIBLE
}


fun View.gone(){
    this.visibility = View.GONE
}

fun MenuItem.gone(){
    this.setVisible(false)
}

fun MenuItem.show(){
    this.setVisible(true)
}










