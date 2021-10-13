package com.wsayan.mvvmstructure.util.language

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import java.util.*

object LanguageHelper {

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private const val SELECTED_COUNTRY = "Locale.Helper.Selected.Country"

    fun onAttach(context: Context): Context {
        val locale = load(context)
        return setLocale(context, locale)
    }

    fun getLocale(context: Context): Locale {
        return load(context)
    }

    fun setLocale(context: Context, locale: Locale): Context {
        persist(context, locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            updateResources(context, locale)
            updateResources1(context, locale)
//            setApplicationLocale(context, locale)
        } else {
            updateResourcesLegacy(context, locale)
        }
    }

    fun isRTL(locale: Locale): Boolean {
        return LanguageLocales.RTL.contains(locale.language)
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LanguageLocales::class.java.name, Context.MODE_PRIVATE)
    }

    private fun persist(context: Context, locale: Locale?) {
        if (locale == null) return
        getPreferences(context)
            .edit()
            .putString(SELECTED_LANGUAGE, locale.language)
            .putString(SELECTED_COUNTRY, locale.country)
            .apply()
    }

    private fun load(context: Context): Locale {
        val preferences = getPreferences(context)
        // TODO: Set Default language here.
        val language = preferences.getString(SELECTED_LANGUAGE, LanguageLocales.BANGLA.language)
        val country = preferences.getString(SELECTED_COUNTRY, LanguageLocales.BANGLA.country)
        return Locale(language, country)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        //context.resources.updateConfiguration(configuration, dm)

        return context.createConfigurationContext(configuration)
    }

    private fun updateResources1(context: Context, locale: Locale): Context {
        var context = context
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    fun setApplicationLocale(context: Context, locale: Locale): Context {
        val resources: Resources = context.resources!!
        val dm: DisplayMetrics = resources.displayMetrics
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, dm)

        return context.createConfigurationContext(config)
    }

    @SuppressWarnings("deprecation")
    private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }

}