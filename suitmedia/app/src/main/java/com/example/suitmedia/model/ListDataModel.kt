package com.example.suitmedia.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.suitmedia.data.DataRepository
import com.example.suitmedia.response.DataItem

class ListDataModel(dataRepository: DataRepository) : ViewModel() {
    val data: LiveData<PagingData<DataItem>> = dataRepository.getData().cachedIn(viewModelScope)
}
