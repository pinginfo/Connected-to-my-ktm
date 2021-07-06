package com.ping.connectedtomyktm.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ping.connectedtomyktm.entities.SendingObject

class DataModel: ViewModel() {
    val selected = MutableLiveData<SendingObject>()

    fun select(data: SendingObject) {
        selected.postValue(data)
    }
}