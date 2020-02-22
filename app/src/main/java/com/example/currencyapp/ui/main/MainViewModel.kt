package com.example.currencyapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.currencyapp.data.state.CurrencyRatesRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val currencyRatesRepository: CurrencyRatesRepository) : ViewModel() {

}
