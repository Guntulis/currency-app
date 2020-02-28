package com.example.currencyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.currencyapp.data.Timer
import com.example.currencyapp.data.api.Resource
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
        currencyRatesAdapter.setHasStableIds(true)
        recyclerView.adapter = currencyRatesAdapter
        currencyRatesAdapter.currencyClickListener = { currencyRate, pos ->
            viewModel.itemWasClicked(currencyRate, pos)
        }
        currencyRatesAdapter.baseValueChangeListener = { baseValue ->
            timer.stopTimer()
            viewModel.setMultiplier(baseValue)
            timer.startTimer(ONE_SECOND)
        }
    }

    override fun onResume() {
        super.onResume()
        timer.startTimer(ONE_SECOND)
    }

    override fun onPause() {
        super.onPause()
        timer.stopTimer()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.uiEvent.observeIt(this) { event ->
            when (event) {
                is UiEvent.MakeItemFirst -> {
                    timer.stopTimer()
                    currencyRatesAdapter.moveItemToTop(event.currencyRate, event.position)
                    recyclerView.scrollToPosition(0)
                    timer.startTimer(ONE_SECOND)
                }
            }
        }
        viewModel.listData.observeIt(this) { currencyRates ->
            when (currencyRates) {
                is Resource.Loading -> {
                    recyclerView.visibility = INVISIBLE
                    errorLabel.visibility = INVISIBLE
                    progressBar.visibility = VISIBLE
                }
                is Resource.Complete -> {
                    recyclerView.visibility = VISIBLE
                    errorLabel.visibility = INVISIBLE
                    progressBar.visibility = INVISIBLE
                    currencyRates.value?.let {
                        currencyRatesAdapter.setItems(it)
                    }
                }
                else -> {
                    recyclerView.visibility = INVISIBLE
                    errorLabel.visibility = VISIBLE
                    progressBar.visibility = INVISIBLE
                }
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
        private const val ONE_SECOND = 1000L
    }
}