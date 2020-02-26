package com.example.currencyapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.currencyapp.data.AppPreferences
import com.example.currencyapp.data.api.Resource
import com.example.currencyapp.data.api.Resource.*
import com.example.currencyapp.data.model.CurrencyRate
import com.example.currencyapp.data.model.CurrencyRate.Companion.TYPE_BASE
import com.example.currencyapp.data.model.CurrencyRate.Companion.TYPE_NORMAL
import com.example.currencyapp.data.model.CurrencyRatesResponse
import com.example.currencyapp.data.state.CurrencyRatesRepository
import com.example.currencyapp.ui.util.SingleEventLiveData
import com.example.currencyapp.ui.util.toCurrencyName
import com.example.currencyapp.ui.util.toFlag
import com.google.gson.Gson

class MainViewModel(
    private val currencyRatesRepository: CurrencyRatesRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _uiEvent = SingleEventLiveData<UiEvent>()
    val uiEvent: LiveData<UiEvent>
        get() = _uiEvent

    private val gson = Gson()
    private val savedResponse = appPreferences.ratesResponse?.let {
        gson.fromJson(it, CurrencyRatesResponse::class.java)
    }

    private var currentRates: List<CurrencyRate>? = toCurrencyRateList(savedResponse)

    val listData: LiveData<Resource<List<CurrencyRate>?>> =
        Transformations.map(currencyRatesRepository.currencyRatesResponseState) { response ->
            when (response) {
                is Complete -> {
                    appPreferences.ratesResponse = gson.toJson(response.value)
                    currentRates = toCurrencyRateList(response.value)
                    currentRates?.let {
                        Complete(currentRates)
                    } ?: Empty()
                }
                else -> {
                    currentRates?.let {
                        Complete(currentRates)
                    } ?: Loading<List<CurrencyRate>?>()
                }
            }
        }

    private fun toCurrencyRateList(ratesResponse: CurrencyRatesResponse?): List<CurrencyRate>? {
        return ratesResponse?.let { response ->
            response.baseCurrency?.let { currencyCode ->
                val rateList = arrayListOf(
                    CurrencyRate(
                        currencyCode.toFlag(),
                        currencyCode,
                        currencyCode.toCurrencyName(),
                        1f,
                        TYPE_BASE
                    )
                )
                rateList.addAll(response.rates?.map { rate ->
                    rate.run {
                        CurrencyRate(
                            key.toFlag(),
                            key,
                            key.toCurrencyName(),
                            calculateValue(value),
                            TYPE_NORMAL
                        )
                    }
                }.orEmpty())
                rateList
            }
        }
    }

    private fun calculateValue(value: Float): Float {
        return appPreferences.multiplier * value
    }

    fun refreshRates() {
        val selectedCurrency = appPreferences.selectedCurrency
        Log.d(TAG, "Refreshing rates for $selectedCurrency")
        currencyRatesRepository.loadCurrencyRates(selectedCurrency)
    }

    fun itemWasClicked(currencyRate: CurrencyRate, position: Int) {
        appPreferences.selectedCurrency = currencyRate.currencyIsoCode
        appPreferences.multiplier = 1
        _uiEvent.value = UiEvent.MakeItemFirst(currencyRate, position)
    }

    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }
}

sealed class UiEvent {
    class MakeItemFirst(val currencyRate: CurrencyRate, val position: Int) : UiEvent()
    object SendOrder : UiEvent()
    object ShowLogin : UiEvent()
}