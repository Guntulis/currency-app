package com.example.currencyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencyapp.data.Timer
import com.example.currencyapp.databinding.MainFragmentBinding
import com.example.currencyapp.ui.adapter.CurrencyRatesAdapter
import com.example.currencyapp.ui.util.observeIt
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var timer: Timer
    @Inject
    lateinit var currencyRatesAdapter: CurrencyRatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return MainFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = currencyRatesAdapter
    }

    override fun onResume() {
        super.onResume()
        timer.startTimer()
    }

    override fun onPause() {
        super.onPause()
        timer.stopTimer()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.listData.observeIt(this) { currencyRates ->
            currencyRates?.let {
                currencyRatesAdapter.setItems(it)
            }
        }
        timer.timerEvent.observeIt(this) { event ->
            when (event) {
                is Timer.TimerEvent.Tick -> {
                    viewModel.refreshRates()
                }
            }
        }
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}