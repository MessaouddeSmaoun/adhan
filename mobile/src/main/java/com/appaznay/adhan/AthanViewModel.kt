package com.appaznay.adhan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AthanViewModel: ViewModel() {


    private val _timer = MutableSharedFlow<String>()
    val timer = _timer.asSharedFlow()

    private var jobPlay: Job = viewModelScope.launch {
        while (true) {
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("HH:mm:ss", Locale.ROOT)
            val current = formatter.format(time)
            _timer.emit(current)
            Log.d("teste",current)
            delay(1000)
        }
    }


    override fun onCleared() {
        super.onCleared()
       if (jobPlay.isActive) jobPlay.cancel()
    }



}