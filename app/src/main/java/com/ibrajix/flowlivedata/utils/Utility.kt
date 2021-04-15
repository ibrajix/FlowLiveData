package com.ibrajix.flowlivedata.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ibrajix.flowlivedata.R

object Utility {

    //display SnackBar
    fun displaySnackBar(view: View, s: String, context: Context) {

        Snackbar.make(view, s, Snackbar.LENGTH_LONG)
            .withColor(ContextCompat.getColor(context, R.color.red))
            .setTextColor(ContextCompat.getColor(context, R.color.white))
            .show()

    }

    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }
}