package com.ping.connectedtomyktm.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IsConnectedModel: ViewModel() {
    // TODO: https://developer.android.com/topic/libraries/architecture/livedata
    val selected = MutableLiveData<Boolean>()

    fun select(bool: Boolean) {
        selected.postValue(bool)
    }
}