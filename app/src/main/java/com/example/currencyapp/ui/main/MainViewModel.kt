package com.example.currencyapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.currencyapp.data.state.CurrencyRatesRepository

class MainViewModel(private val currencyRatesRepository: CurrencyRatesRepository) : ViewModel() {

    fun loadCurrencyRates(baseCurrency: String?) {
        currencyRatesRepository.loadCurrencyRates(baseCurrency)
    }
}