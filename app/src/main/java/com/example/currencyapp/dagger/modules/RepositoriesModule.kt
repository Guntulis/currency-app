package com.example.currencyapp.dagger.modules

import android.app.Application
import android.content.Context
import com.example.currencyapp.data.AppPreferences
import com.example.currencyapp.data.api.ApiClient
import com.example.currencyapp.data.state.CurrencyRatesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule(
    private val application: Application
) {

    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppPreferences(context: Context): AppPreferences {
        return AppPreferences(context)
    }


    @Provides
    @Singleton
    fun provideCurrencyRatesRepository(
        apiClient: ApiClient
    ): CurrencyRatesRepository {
        return CurrencyRatesRepository(apiClient)
    }
}