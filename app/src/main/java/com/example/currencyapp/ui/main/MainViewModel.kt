package com.example.currencyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.currencyapp.R
import com.example.currencyapp.data.api.Resource
import com.example.currencyapp.data.api.Resource.Complete
import com.example.currencyapp.data.model.CurrencyRate
import com.example.currencyapp.data.model.CurrencyRatesResponse
import com.example.currencyapp.data.state.CurrencyRatesRepository

class MainViewModel(private val currencyRatesRepository: CurrencyRatesRepository) : ViewModel() {

    val currencyRatesResponse: LiveData<Resource<CurrencyRatesResponse>>
        get() = currencyRatesRepository.currencyRatesResponseState

    val listData: LiveData<List<CurrencyRate>> = Transformations.map(currencyRatesRepository.currencyRatesResponseState) { response ->
        when (response) {
            is Complete -> {
                response.value.rates?.map { rate ->
                    CurrencyRate(resolveFlag(rate.key), rate.key, rate.key, rate.value )
                }.orEmpty()
            }
            else -> emptyList()
        }
    }

    private fun resolveFlag(countryCode: String): Int? {
        return when (countryCode) {
            "AUD" -> R.drawable.ic_australia
            "BGN" -> R.drawable.ic_bulgaria
            "BRL" -> R.drawable.ic_brazil
            "CAD" -> R.drawable.ic_canada
            "CHF" -> R.drawable.ic_switzerland
            "CNY" -> R.drawable.ic_china
            "CZK" -> R.drawable.ic_czech_republic
            "DKK" -> R.drawable.ic_denmark
            "GBP" -> R.drawable.ic_united_kingdom
            "HKD" -> R.drawable.ic_hong_kong
            "HRK" -> R.drawable.ic_croatia
            "HUF" -> R.drawable.ic_hungary
            "IDR" -> R.drawable.ic_indonesia
            "ILS" -> R.drawable.ic_israel
            "INR" -> R.drawable.ic_india
            "ISK" -> R.drawable.ic_ireland
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

    fun loadCurrencyRates(baseCurrency: String?) {
        currencyRatesRepository.loadCurrencyRates(baseCurrency)
    }
}