package com.example.jetfood.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfood.data.FoodRepository
import com.example.jetfood.model.Food
import com.example.jetfood.model.OrderFood
import com.example.jetfood.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailFoodViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderFood>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderFood>>
        get() = _uiState

    fun getRewardById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderFoodById(rewardId))
        }
    }

    fun addToCart(food: Food, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFood(food.id, count)
        }
    }
}