package com.app.poojastoreowner

import android.view.View

fun View.visibleOrHide(show: Boolean) {
    if (show) visible() else gone()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}