package com.example.currencyapp.ui.util

import com.example.currencyapp.R

fun String.toFlag(): Int {
    return when (this) {
        "AUD" -> R.drawable.ic_australia
        "BGN" -> R.drawable.ic_bulgaria
        "BRL" -> R.drawable.ic_brazil
        "CAD" -> R.drawable.ic_canada
        "CHF" -> R.drawable.ic_switzerland
        "CNY" -> R.drawable.ic_china
        "CZK" -> R.drawable.ic_czech_republic
        "DKK" -> R.drawable.ic_denmark
        "EUR" -> R.drawable.ic_european_union
        "GBP" -> R.drawable.ic_united_kingdom
        "HKD" -> R.drawable.ic_hong_kong
        "HRK" -> R.drawable.ic_croatia
        "HUF" -> R.drawable.ic_hungary
        "IDR" -> R.drawable.ic_indonesia
        "ILS" -> R.drawable.ic_israel
        "INR" -> R.drawable.ic_india
        "ISK" -> R.drawable.ic_iceland
        "JPY" -> R.drawable.ic_japan
        "KRW" -> R.drawable.ic_south_korea
        "MXN" -> R.drawable.ic_mexico
        "MYR" -> R.drawable.ic_malaysia
        "NOK" -> R.drawable.ic_norway
        "NZD" -> R.drawable.ic_new_zealand
        "PHP" -> R.drawable.ic_philippines
        "PLN" -> R.drawable.ic_poland
        "RON" -> R.drawable.ic_romania
        "RUB" -> R.drawable.ic_russia
        "SEK" -> R.drawable.ic_sweden
        "SGD" -> R.drawable.ic_singapore
        "THB" -> R.drawable.ic_thailand
        "USD" -> R.drawable.ic_united_states
        "ZAR" -> R.drawable.ic_south_africa
        else -> R.drawable.ic_flag
    }
}

fun String.toCurrencyName(): Int {
    return when (this) {
        "AUD" -> R.string.australia
        "BGN" -> R.string.bulgaria
        "BRL" -> R.string.brazil
        "CAD" -> R.string.canada
        "CHF" -> R.string.switzerland
        "CNY" -> R.string.china
        "CZK" -> R.string.czech_republic
        "DKK" -> R.string.denmark
        "EUR" -> R.string.european_union
        "GBP" -> R.string.united_kingdom
        "HKD" -> R.string.hong_kong
        "HRK" -> R.string.croatia
        "HUF" -> R.string.hungary
        "IDR" -> R.string.indonesia
        "ILS" -> R.string.israel
        "INR" -> R.string.india
        "ISK" -> R.string.iceland
        "JPY" -> R.string.japan
        "KRW" -> R.string.south_korea
        "MXN" -> R.string.mexico
        "MYR" -> R.string.malaysia
        "NOK" -> R.string.norway
        "NZD" -> R.string.new_zealand
        "PHP" -> R.string.philippines
        "PLN" -> R.string.poland
        "RON" -> R.string.romania
        "RUB" -> R.string.russia
        "SEK" -> R.string.sweden
        "SGD" -> R.string.singapore
        "THB" -> R.string.thailand
        "USD" -> R.string.united_states
        "ZAR" -> R.string.south_africa
        else -> R.string.unknown
    }
}