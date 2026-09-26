package com.kelvsricafort101.wordpress.inventory.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kelvsricafort101.wordpress.inventory.R

enum class AppLanguage(
    val code: String,
    @StringRes val displayNameRes: Int,
    @DrawableRes val flagRes: Int
) {
    ENGLISH(
        code = "en",
        displayNameRes = R.string.language_english,
        flagRes = R.drawable.flag_english
    ),
    FILIPINO(
        code = "fil",
        displayNameRes = R.string.language_filipino,
        flagRes = R.drawable.flag_filipino
    ),
    SPANISH(
        code = "es",
        displayNameRes = R.string.language_spanish,
        flagRes = R.drawable.flag_spanish
    )
}