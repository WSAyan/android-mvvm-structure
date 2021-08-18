package com.wsayan.mvvmstructure.util

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.wsayan.mvvmstructure.R

/**
 * Created by Screenshot on 8/17/16.
 */
class ProgressBarHandler {
    private var mProgressBar: ProgressBar? = null
    private var mContext: Context
    var color = 0

    constructor(context: Context) {
        mContext = context
        initialize("")
    }

    constructor(context: Context, color: Int) {
        mContext = context
        this.color = color
        initialize("")
    }

    constructor(context: Context, text: String) {
        mContext = context
        initialize(text)
    }

    private fun initialize(text: String) {
        val layout =
            (mContext as AppCompatActivity).findViewById<View>(android.R.id.content).rootView as ViewGroup
        mProgressBar = ProgressBar(mContext, null, android.R.attr.progressBarStyleLarge)
        mProgressBar?.isIndeterminate = true
        val colorr =
            if (color != 0) color else ContextCompat.getColor(mContext, R.color.teal_200)
        mProgressBar?.indeterminateDrawable?.setColorFilter(colorr, PorterDuff.Mode.SRC_IN)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        val rl = RelativeLayout(mContext)
        rl.gravity = Gravity.CENTER
        rl.addView(mProgressBar)
        layout.addView(rl, params)
        hide()
    }

    fun show() {
        mProgressBar?.visibility = View.VISIBLE
        (mContext as Activity).window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hide() {
        mProgressBar!!.visibility = View.INVISIBLE
        (mContext as Activity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}