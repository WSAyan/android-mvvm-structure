package com.wsayan.mvvmstructure.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun Context.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) =
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

fun ImageView.loadNetworkImage(
    context: Context,
    url: String = "https://via.placeholder.com/150?text=error"
) = Glide
    .with(context)
    .load(url)
    .into(this)


