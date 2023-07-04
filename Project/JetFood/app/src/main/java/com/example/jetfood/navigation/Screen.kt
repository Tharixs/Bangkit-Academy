package com.example.jetfood.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Chart : Screen("chart")
    object Profile : Screen("profile")
    object DetailFood : Screen("home/{foodId}") {
        fun createRoute(foodId: Long) = "home/$foodId"
    }
}


