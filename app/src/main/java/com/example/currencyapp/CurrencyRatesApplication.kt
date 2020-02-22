package com.example.currencyapp

import android.app.Application
import com.example.currencyapp.dagger.components.DaggerApplicationComponent


class CurrencyRatesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.create().inject(this)
    }
}