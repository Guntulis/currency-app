package com.example.currencyapp.data

import android.os.Handler
import androidx.lifecycle.LiveData
import com.example.currencyapp.BuildConfig
import com.example.currencyapp.ui.util.SingleEventLiveData

class Timer {

    private val _timerEvent = SingleEventLiveData<TimerEvent>()
    val timerEvent: LiveData<TimerEvent>
        get() = _timerEvent

    private val handler: Handler = Handler()

    private val task = object : Runnable {
        override fun run() {
            _timerEvent.value = TimerEvent.Tick
            handler.postDelayed(this, BuildConfig.REFRESH_TIME_MS)
        }
    }

    fun startTimer(delay: Long) {
        handler.postDelayed(task, delay)
    }

    fun stopTimer() {
        handler.removeCallbacks(task)
    }

    sealed class TimerEvent {
        object Tick : TimerEvent()
    }
}