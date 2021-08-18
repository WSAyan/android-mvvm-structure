package com.wsayan.mvvmstructure.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.wsayan.mvvmstructure.R

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()


fun EditText.showKeyboard(delay: Long = 100) =
    this.postDelayed({
        this.requestFocus()
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            this,
            InputMethodManager.SHOW_IMPLICIT
        )
    }, 100)


fun Activity.hideKeyboard() {
    val inputManager = getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    val focusedView = currentFocus

    if (focusedView != null) {
        inputManager.hideSoftInputFromWindow(
            focusedView.windowToken,
            InputMethodManager.RESULT_HIDDEN
        )
    }
}

fun Context?.hasPermissions(vararg permissions: Array<String>): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this != null) {
        Log.e("log per", "granted 1")
        for (permission in permissions[0]) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("log per", "granted 2")
                return false
            }
        }
    }
    return true
}

fun View.snacksbarView(message: String, flag: Boolean) {
    if (flag) {
        val snackbar = Snackbar
            .make(this, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.show()
    } else if (!flag) {
        val snackbar = Snackbar
            .make(this, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

}