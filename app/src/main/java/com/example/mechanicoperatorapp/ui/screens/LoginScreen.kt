package com.example.mechanicoperatorapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mechanicoperatorapp.R

@Composable
fun LoginScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.field),
                contentScale = ContentScale.FillBounds
            )
    ) {

        Text(
            text = "Добро пожаловать!",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            lineHeight = 37.sp,
            modifier = Modifier.padding(34.dp)
        )

        Surface(
            shape = RoundedCornerShape(0.dp, 40.dp, 0.dp, 0.dp),
            color = Color(0xDADADAA3),
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .blur(7.5.dp)
        ) {

        }

    }

}