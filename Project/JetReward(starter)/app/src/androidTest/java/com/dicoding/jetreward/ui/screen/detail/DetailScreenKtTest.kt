package com.dicoding.jetreward.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dicoding.jetreward.model.OrderReward
import com.dicoding.jetreward.model.Reward
import com.dicoding.jetreward.R
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderRewards = OrderReward(
        reward = Reward(4, R.drawable.reward_4, "Jaket Hoodie Dicoding", 7500),
        count = 0
    )

    @Before
    fun setUp(){
        composeTestRule.setContent {
            JetRewardTheme {
                DetailContent(
                    image = fakeOrderRewards.reward.image,
                    title = fakeOrderRewards.reward.title,
                    basePoint = fakeOrderRewards.reward.requiredPoint,
                    count = fakeOrderRewards.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed(){
        composeTestRule.onNodeWithText(fakeOrderRewards.reward.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.required_point,
                fakeOrderRewards.reward.requiredPoint
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled(){
        composeTestRule.onNodeWithContentDescription("Order button").assertIsNotEnabled()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.plus_symbol))
            .performClick()
        composeTestRule.onNodeWithContentDescription("Order button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.plus_symbol)).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }
    @Test
    fun decreaseProduct_correctCounter(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.minus_symbol)).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }


}