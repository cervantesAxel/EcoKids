package com.example.eco_kids.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import com.example.eco_kids.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//Pantalla inicial
@Composable
fun SplashScreen(onContinue: () -> Unit) {
    val scale = remember { Animatable(0.6f) } //Logo
    val textAlpha = remember { Animatable(0f) } //Texto
    var enabled by remember { mutableStateOf(false) }

    //Texto
    LaunchedEffect(Unit) {
        delay(700)
        textAlpha.animateTo(
            1f,
            animationSpec = tween(500)
        )
        enabled = true
    }

    //Logo
    LaunchedEffect(Unit) {
        // Sube de más
        scale.animateTo(
            targetValue = 1.20f,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )

        // Baja un poco
        scale.animateTo(
            targetValue = 0.80f,
            animationSpec = tween(
                durationMillis = 200,
                easing = FastOutSlowInEasing
            )
        )

        // Se acomoda en el tamaño final
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 200,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .clickable { onContinue() },
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_fondo_inicio),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.ic_logo_inicio),
            contentDescription = "Logo de la app",
            modifier = Modifier
                .scale(scale.value)
                .size(500.dp)
        )

        Text(
            text = "Pulsa para continuar",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 70.dp)
                .alpha(textAlpha.value),
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
