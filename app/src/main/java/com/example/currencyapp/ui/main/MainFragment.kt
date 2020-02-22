package com.example.currencyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencyapp.databinding.MainFragmentBinding
import dagger.android.support.DaggerFragment

class MainFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return MainFragmentBinding.inflate(inflater, container, false).root
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}