package com.example.currencyapp

import android.app.Application
import com.example.currencyapp.dagger.component.ApplicationComponent
import com.example.currencyapp.dagger.component.DaggerApplicationComponent
import com.example.currencyapp.dagger.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CurrencyRatesApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder().appModule(AppModule(this)).build()
        appComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}