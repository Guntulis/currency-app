package com.example.currencyapp.data.model

data class CurrencyRate(
    val flagResId: Int,
    val currencyIsoCode: String,
    val currencyNameResId: Int,
    val rate: Float,
    var type: Int = TYPE_NORMAL
) {
    companion object {
        const val TYPE_BASE = 1
        const val TYPE_NORMAL = 2
    }
}