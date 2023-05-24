package com.dicoding.jetreward

import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.jetreward.model.FakeRewardDataSource
import com.dicoding.jetreward.navigation.Screen
import com.dicoding.jetreward.ui.components.RewardItem
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetRewardAppKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetRewardTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetRewardApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Home.route, currentRoute)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("RewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
        val curRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.DetailReward.route, curRoute)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title)
            .assertIsDisplayed()
    }

    @Test
    fun navHost_menuBottom_correctNavigation() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_cart))
            .performClick()
        val curRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Cart.route, curRoute)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_home))
            .performClick()
        val curRoute2 = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Home.route, curRoute2)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_profile))
            .performClick()
        val curRoute3 = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Profile.route, curRoute3)
    }

    @Test
    fun navHost_clickBackButton_navigatesToHome() {
        composeTestRule.onNodeWithTag("RewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
        val curRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.DetailReward.route, curRoute)
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        val curRoute1 = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Home.route, curRoute1)
    }

    @Test
    fun when_add_toChart_correctNavHos() {
        composeTestRule.onNodeWithTag("RewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
        val curRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.DetailReward.route, curRoute)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.plus_symbol))
            .performClick()
        composeTestRule.onNodeWithContentDescription("Order button").assertIsEnabled()
            .performClick()
        val curRoute1 = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Cart.route, curRoute1)
    }


}