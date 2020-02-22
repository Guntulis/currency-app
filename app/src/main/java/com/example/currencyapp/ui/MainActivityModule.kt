package com.example.currencyapp.ui

import com.example.currencyapp.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector()
    abstract fun mainFragment(): MainFragment
}