package com.wsayan.mvvmstructure.util.language

import java.util.*

object LanguageLocales {
    val English: Locale by lazy { Locale("en", "US") }
    val BANGLA: Locale by lazy { Locale("bn", "BD") }

    val RTL: Set<String> by lazy {
        hashSetOf(
            "ar",
            "ku",
            "dv",
            "fa",
            "ha",
            "he",
            "iw",
            "ji",
            "ps",
            "sd",
            "ug",
            "ur",
            "yi"
        )
    }
}