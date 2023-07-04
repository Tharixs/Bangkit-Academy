package com.example.jetfood.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetfood.di.Injection
import com.example.jetfood.model.OrderFood
import com.example.jetfood.ui.ViewModelFactory
import com.example.jetfood.ui.common.UiState
import com.example.jetfood.ui.components.FoodItem
import com.example.jetfood.ui.components.SearchBar
import com.example.jetfood.ui.theme.JetFoodTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllRewards()
            }
            is UiState.Success -> {
                HomeContent(
                    orderFood = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    viewModel = viewModel
                )
            }
            is UiState.Error -> {}
        }

    }
}

@Composable
fun HomeContent(
    orderFood: List<OrderFood>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    viewModel: HomeViewModel
) {

    val query by viewModel.query
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchBar(
            query = query,
            onQueryChange = viewModel::searchFood,
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(155.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(orderFood) { data ->
                FoodItem(
                    image = data.food.image,
                    title = data.food.title,
                    price = data.food.price,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.food.id)
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    JetFoodTheme() {
        HomeScreen(
            navigateToDetail = {})
    }
}