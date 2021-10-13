package com.wsayan.mvvmstructure.util.language

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.view.View
import com.wsayan.mvvmstructure.ui.main.MainActivity
import java.util.*

interface LocaleHelperActivityDelegate {
    fun setLocale(activity: Activity, newLocale: Locale, isActivityRestart: Boolean)
    fun attachBaseContext(newBase: Context): Context
    fun applyOverrideConfiguration(
        baseContext: Context,
        overrideConfiguration: Configuration?
    ): Configuration?

    fun onPaused()
    fun onResumed(activity: Activity)
    fun onCreate(activity: Activity)
}

class LocaleHelperActivityDelegateImpl : LocaleHelperActivityDelegate {
    override fun onCreate(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.window.decorView.layoutDirection =
                if (LanguageHelper.isRTL(Locale.getDefault()))
                    View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
        }
    }

    private var locale: Locale = Locale.getDefault()

    override fun setLocale(activity: Activity, newLocale: Locale, isActivityRestart: Boolean) {
        LanguageHelper.setLocale(activity, newLocale)
        locale = newLocale
//        activity.recreate()
        if (isActivityRestart) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

        }
    }

    fun setCalLocale(activity: Activity, newLocale: Locale) {
        LanguageHelper.setLocale(activity, newLocale)
    }

    override fun attachBaseContext(newBase: Context): Context = LanguageHelper.onAttach(newBase)

    override fun applyOverrideConfiguration(
        baseContext: Context, overrideConfiguration: Configuration?
    ): Configuration? {
        if (overrideConfiguration != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        return overrideConfiguration
    }

    override fun onPaused() {
//        locale = Locale.getDefault()
    }

    override fun onResumed(activity: Activity) {
//        if (locale == Locale.getDefault()) return
//        activity.recreate()
    }
}

class LocaleHelperApplicationDelegate {
    fun attachBaseContext(base: Context): Context = LanguageHelper.onAttach(base)

    fun onConfigurationChanged(context: Context) {
        LanguageHelper.onAttach(context)
    }
}