package com.example.currencyapp.data.model

data class CurrencyRate(

    val flagResId : Int,
    val currencyIsoCode : String,
    val currencyNameResId : Int,
    val rate : Float
)