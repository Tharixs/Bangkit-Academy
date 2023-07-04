package com.example.jetfood.ui.screen.chart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetfood.R
import com.example.jetfood.di.Injection
import com.example.jetfood.ui.ViewModelFactory
import com.example.jetfood.ui.common.UiState
import com.example.jetfood.ui.components.ChartItem
import com.example.jetfood.ui.components.OrderButtonFood

@Composable
fun ChartScreen(
    viewModel: ChartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderFoods()
            }
            is UiState.Success -> {
                ChartContent(
                    uiState.data,
                    onProductCountChanged = { foodId, count ->
                        viewModel.updateOrderFood(foodId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun ChartContent(
    state: ChartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier,
    onOrderButtonClicked: (String) -> Unit,
) {
    val sharedMessage = stringResource(
        R.string.share_message,
        state.orderFood.count(),
        state.price
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
            Text(
                text = stringResource(R.string.menu_chart),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        OrderButtonFood(
            text = stringResource(R.string.total_pesanan, state.price),
            enabled = state.orderFood.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(sharedMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.orderFood, key = { it.food.id }) { item ->
                ChartItem(
                    foodId = item.food.id,
                    image = item.food.image,
                    title = item.food.title,
                    price = item.food.price * item.count,
                    quantity = item.count,
                    onProductQuantityChange = onProductCountChanged,
                )
                Divider()
            }
        }
    }
}