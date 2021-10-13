package com.wsayan.mvvmstructure.ui.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wsayan.mvvmstructure.di.DataManager
import com.wsayan.mvvmstructure.util.language.LocaleHelperActivityDelegateImpl
import com.wsayan.mvvmstructure.util.ProgressBarHandler
import com.wsayan.mvvmstructure.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var dataManager: DataManager
    val localeDelegate = LocaleHelperActivityDelegateImpl()

    var progressBarHandler: ProgressBarHandler? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localeDelegate.onCreate(this)
        progressBarHandler = ProgressBarHandler(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewRelatedTask()
    }

    abstract fun viewRelatedTask()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localeDelegate.attachBaseContext(base))
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        super.applyOverrideConfiguration(
            localeDelegate.applyOverrideConfiguration(baseContext, overrideConfiguration)
        )
    }


    override fun onResume() {
        super.onResume()
        localeDelegate.onResumed(this)
    }

    override fun onPause() {
        super.onPause()
        localeDelegate.onPaused()
    }

    open fun updateLocale(locale: Locale, isActivityRestart: Boolean) {
        localeDelegate.setLocale(this, locale, isActivityRestart)
    }

    open fun updateCalLocale(locale: Locale) {
        localeDelegate.setCalLocale(this, locale)
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back)
    }


    fun addFragment(isReplace: Boolean, container: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (isReplace) {
            transaction.replace(container, fragment)
        } else {
            transaction.add(container, fragment)
        }
        transaction.commit()
    }

    fun showDialog(isCancelAble: Boolean, dialogFragment: BaseDialogFragment) {
        dialogFragment.isCancelable = isCancelAble
        dialogFragment.show(supportFragmentManager.beginTransaction(), "dialog")
    }

}