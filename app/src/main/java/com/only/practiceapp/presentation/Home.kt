package com.only.practiceapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.only.practiceapp.R
import com.only.practiceapp.ui.theme.Cloudy1
import com.only.practiceapp.ui.theme.Cloudy2
import com.only.practiceapp.ui.theme.ParCloudy
import com.only.practiceapp.ui.theme.Sunny1
import com.only.practiceapp.ui.theme.Sunny2
import com.only.practiceapp.ui.theme.Typography

@Composable
fun Home(){

    val color = listOf(Sunny1, Sunny2)
    val color2 = listOf(Cloudy1, Cloudy2)
    val color3 = listOf(ParCloudy, ParCloudy)

    val title = "Mumbai"

    var textCol : Color = Color.Black

    if(title == "Mumbai"){
        textCol = Color.White
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(color)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                title,
                style = Typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = textCol,
                fontSize = 20.sp,
            )
            Text(
                "11:00",
                style = Typography.bodySmall,
                color = textCol,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.padding(50.dp))

        Image(
            painter = painterResource(R.drawable.sun),
            contentDescription = null
        )

        Spacer(Modifier.padding(15.dp))

        Text(
            "Sunny",
                style = Typography.bodySmall,
            color = textCol,
            fontSize = 30.sp
        )

        Spacer(Modifier.padding(15.dp))

        Text(
            "25\u2103",
                style = Typography.bodySmall,
            color = textCol,
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.padding(30.dp))

        Image(
            painter = painterResource(R.drawable.graph),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Home()
}