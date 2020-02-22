package com.example.currencyapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyRatesResponse(

    @SerializedName("baseCurrency")
    @Expose
    val baseCurrency: String? = null,

    @SerializedName("rates")
    @Expose
    val rates: Map<String, Float>? = null

)