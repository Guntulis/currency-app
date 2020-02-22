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
            else -> R.drawable.ic_flag
        }
    }

    fun loadCurrencyRates(baseCurrency: String?) {
        currencyRatesRepository.loadCurrencyRates(baseCurrency)
    }
}