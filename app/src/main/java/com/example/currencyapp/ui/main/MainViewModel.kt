package com.example.currencyapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.currencyapp.R
import com.example.currencyapp.data.AppPreferences
import com.example.currencyapp.data.api.Resource
import com.example.currencyapp.data.api.Resource.Complete
import com.example.currencyapp.data.model.CurrencyRate
import com.example.currencyapp.data.model.CurrencyRatesResponse
import com.example.currencyapp.data.state.CurrencyRatesRepository
import com.google.gson.Gson

class MainViewModel(
    private val currencyRatesRepository: CurrencyRatesRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    val currencyRatesResponse: LiveData<Resource<CurrencyRatesResponse>>
        get() = currencyRatesRepository.currencyRatesResponseState

    private val gson = Gson()
    private val savedResponse =
        gson.fromJson(appPreferences.ratesResponse, CurrencyRatesResponse::class.java)
    private val savedList = toCurrencyRateList(savedResponse?.rates)

    val listData: LiveData<List<CurrencyRate>> =
        Transformations.map(currencyRatesRepository.currencyRatesResponseState) { response ->
            when (response) {
                is Complete -> {
                    appPreferences.ratesResponse = gson.toJson(response.value)
                    toCurrencyRateList(response.value.rates)
                }
                else -> {
                    savedList
                }
            }
        }

    private fun toCurrencyRateList(rates: Map<String, Float>?): List<CurrencyRate> {
        return rates?.map { rate ->
            rate.run {
                CurrencyRate(resolveFlag(key), key, resolveCurrencyName(key), value)
            }
        }.orEmpty()
    }

    private fun resolveFlag(countryCode: String): Int {
        return when (countryCode) {
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

    private fun resolveCurrencyName(countryCode: String): Int {
        return when (countryCode) {
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

    fun refreshRates() {
        Log.d(TAG, "Refreshing rates")
        currencyRatesRepository.loadCurrencyRates("EUR")
    }

    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }
}