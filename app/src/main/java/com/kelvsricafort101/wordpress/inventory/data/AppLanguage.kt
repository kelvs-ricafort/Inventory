package com.kelvsricafort101.wordpress.inventory.data

import androidx.annotation.DrawableRes
import com.kelvsricafort101.wordpress.inventory.R

enum class AppLanguage(
    val code: String,
    val displayName: String,
    @DrawableRes val flagRes: Int
) {
    ENGLISH(
        code = "en",
        displayName = "English",
        flagRes = R.drawable.flag_english
    ),
    FILIPINO(
        code = "fil",
        displayName = "Filipino",
        flagRes = R.drawable.flag_filipino
    ),
    SPANISH(
        code = "es",
        displayName = "Spanish",
        flagRes = R.drawable.flag_spanish
    )
}