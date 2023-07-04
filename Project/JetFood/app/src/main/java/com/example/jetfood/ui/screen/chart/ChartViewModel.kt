package com.example.jetfood.ui.screen.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfood.data.FoodRepository
import com.example.jetfood.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChartViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ChartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ChartState>>
        get() = _uiState

    fun getAddedOrderFoods() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderFoods()
                .collect { orderFood ->
                    val price =
                        orderFood.sumOf { it.food.price * it.count }
                    _uiState.value = UiState.Success(ChartState(orderFood, price))
                }
        }
    }

    fun updateOrderFood(foodId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFood(foodId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderFoods()
                    }
                }
        }
    }
}