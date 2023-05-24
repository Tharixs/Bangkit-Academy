package com.example.mycomposetesting.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.mycomposetesting.R
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.mycomposetesting.CalculatorApp
import com.example.mycomposetesting.ui.theme.MyComposeTestingTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalculatorAppTest {
    @get:Rule
//    val composeTestRule = createComposeRule()
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {

        composeTestRule.setContent {
            MyComposeTestingTheme {
                CalculatorApp()
            }
        }
    }

    @Test
    fun calculate_area_of_rectangle_correct() {
        composeTestRule.onNodeWithText("Masukkan panjang").performTextInput("10")
        composeTestRule.onNodeWithText("Masukkan lebar").performTextInput("5")
        composeTestRule.onNodeWithText("Hitung!").performClick()
        composeTestRule.onNodeWithText("Hasil: 50.0").assertExists()
    }

    @Test
    fun calculate_area_of_rectangle_corect() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.enter_length)).performTextInput("1")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.enter_width)).performTextInput("2")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.count)).performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.count), useUnmergedTree = true).assertHasNoClickAction()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.result, 2.0)).assertExists()
    }
    @Test
    fun wrong_input_not_calculate(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.enter_length)).performTextInput("..1")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.enter_width)).performTextInput("2")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.count)).performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.result, 0.0)).assertExists()

    }
}