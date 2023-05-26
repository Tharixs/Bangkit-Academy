package com.example.jetfood.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetfood.R
import com.example.jetfood.ui.theme.JetFoodTheme
import com.example.jetfood.ui.theme.Shapes

@Composable
fun ChartItem(
    foodId: Long,
    image: Int,
    title: String,
    price: Int,
    quantity: Int,
    onProductQuantityChange: (id: Long, quantity: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = "Rp. $price",
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
        }
        FoodQuantity(
            orderId = foodId,
            count = quantity,
            onFoodIncreased = { onProductQuantityChange(foodId, quantity + 1) },
            onFoodDecreased = { onProductQuantityChange(foodId, quantity - 1) }
        )
    }

}

@Composable
@Preview(showBackground = true)
fun ChartItemPreview() {
    JetFoodTheme {
        ChartItem(
            1, R.drawable.burger, "Burger", 45000, 1,
            onProductQuantityChange = { foodId, quantity -> },
        )
    }
}