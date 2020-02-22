package com.example.currencyapp.dagger.component

import com.example.currencyapp.CurrencyRatesApplication
import com.example.currencyapp.dagger.module.AppModule
import com.example.currencyapp.dagger.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent {
    fun inject(application: CurrencyRatesApplication)
}