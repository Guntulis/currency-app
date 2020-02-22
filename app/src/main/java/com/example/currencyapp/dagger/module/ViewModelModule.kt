package com.example.currencyapp.dagger.module

import androidx.lifecycle.ViewModel
import com.example.currencyapp.dagger.annotation.ViewModelKey
import com.example.currencyapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModel
}