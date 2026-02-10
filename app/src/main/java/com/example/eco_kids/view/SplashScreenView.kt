package com.example.eco_kids.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import com.example.eco_kids.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    val scale = remember { Animatable(0.5f) }

    LaunchedEffect (Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            )
        )
        delay(500)
        onFinish()
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0x2834FA50)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_ecokids_inicio),
            contentDescription = "Logo de la app",
            modifier = Modifier
                .scale(scale.value)
                .size(450.dp)
        )

    }
}
