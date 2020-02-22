package com.example.currencyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyapp.data.Timer
import com.example.currencyapp.data.api.Resource.*
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
        initRecyclerView()

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
        viewModel.currencyRatesResponse.observeIt(this) { state ->
            when (state) {
                is Complete -> {
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
                is Loading -> {
                    progressBar.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    recyclerView.visibility = View.INVISIBLE
                }
                is Error -> {
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }
        viewModel.listData.observeIt(this) { currencyRates ->
            currencyRates?.let { currencyRatesAdapter.setItems(it) }
        }
        timer.timerEvent.observeIt(this) { event ->
            when (event) {
                is Timer.TimerEvent.Tick -> {
                    viewModel.refreshRates()
                }
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        currencyRatesAdapter.currencyClickListener = { product ->
            //navigation.navigateToProductOptions(product, null)
        }
        recyclerView.adapter = currencyRatesAdapter
        recyclerView.itemAnimator = null
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}