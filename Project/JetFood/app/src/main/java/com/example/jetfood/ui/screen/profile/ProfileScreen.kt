package com.example.jetfood.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jetfood.R
import com.example.jetfood.ui.theme.JetFoodTheme
import kotlin.math.round

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            AsyncImage(
                model = "https://lh3.googleusercontent.com/a/AAcHTtcbVqVyaXTYXuDAbrSQedru623ey54Md7MI_RK4ow=s432-c-no",
                contentDescription = stringResource(R.string.profile_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.profile_name),
                fontFamily = typography.h1.fontFamily,
                fontWeight = typography.h1.fontWeight,
                fontSize = 24.sp,
            )
            Text(
                text = stringResource(id = R.string.profile_email),
                fontFamily = typography.h5.fontFamily,
                fontWeight = typography.h5.fontWeight,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    JetFoodTheme {
        ProfileScreen()
    }
}