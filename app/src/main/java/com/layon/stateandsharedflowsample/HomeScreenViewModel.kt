package com.layon.stateandsharedflowsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    private val _liveData = MutableLiveData("Hello World!")
    val liveData: LiveData<String> = _liveData
    private var _countLiveData = 0

    private val _stateFlow = MutableStateFlow("Hello World!")
    val stateFlow = _stateFlow.asStateFlow()
    private var _countSateFlow = 0

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()
    private var _countSharedFlow = 0

    fun triggerLiveData() {
        _liveData.value = "LiveData ${++_countLiveData}"
    }

    fun triggerStateFlow() {
        _stateFlow.value = "StateFlow ${++_countSateFlow}"
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow ${++_countSharedFlow}")
        }
    }

    fun triggerFlow() : Flow<String> {
        return flow {
            repeat(10) {
                emit("Item $it")
                delay(1000L)
            }
        }
    }
}