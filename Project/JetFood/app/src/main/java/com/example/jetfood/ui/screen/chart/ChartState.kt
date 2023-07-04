package com.example.jetfood.ui.screen.chart

import com.example.jetfood.model.OrderFood

data class ChartState(
    val orderFood: List<OrderFood>,
    val price: Int
)