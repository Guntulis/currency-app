package com.example.currencyapp.dagger.components

import com.example.currencyapp.CurrencyRatesApplication
import dagger.Component

@Component
interface ApplicationComponent {
    fun inject(application: CurrencyRatesApplication)
}