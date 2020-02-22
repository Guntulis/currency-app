package com.example.currencyapp.data.state

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyapp.data.api.ApiClient
import com.example.currencyapp.data.api.Resource
import com.example.currencyapp.data.model.CurrencyRatesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CurrencyRatesRepository(private val apiClient: ApiClient) {

    private val _currencyRatesResponseState = MutableLiveData<Resource<CurrencyRatesResponse>>()
    val currencyRatesResponseState: LiveData<Resource<CurrencyRatesResponse>>
        get() = _currencyRatesResponseState

    private val _compositeDisposable = CompositeDisposable()

    fun loadCurrencyRates(baseCurrency: String?) {
        if (baseCurrency == null) {
            _currencyRatesResponseState.value = Resource.Error("Base currency is null")
        } else {
            _currencyRatesResponseState.value = Resource.Loading()
            apiClient.getCurrencyRates(baseCurrency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { currencyRatesResponse ->
                        currencyRatesResponse?.let {
                            _currencyRatesResponseState.value = Resource.Complete(it)
                        }
                    },
                    { error ->
                        val errorMessage = "Failed to get currency rates"
                        _currencyRatesResponseState.value = Resource.Error(errorMessage)
                        Log.e(TAG, errorMessage, error)
                    }
                ).also { _compositeDisposable.add(it) }
        }
    }

    companion object {
        val TAG: String = CurrencyRatesRepository::class.java.simpleName
    }
}