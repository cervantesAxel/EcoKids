package com.example.eco_kids.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eco_kids.R
import com.example.eco_kids.ui.theme.CurvedTopShape

@Composable
fun NameScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_fondo_inicio),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 36.dp,
                    top = 180.dp,
                    end = 36.dp,
                    bottom = 300.dp
                )
                .clip(CurvedTopShape)
                .background(Color(0xFFFEFBE8))
                .align(Alignment.Center)
        )
    }
}