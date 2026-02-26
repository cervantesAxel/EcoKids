package com.example.eco_kids.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_kids.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun CamionScreen(
    onGoToGames: () -> Unit
) {
    var puntos by remember { mutableIntStateOf(0) }
    var vidas by remember { mutableIntStateOf(3) }
    var juegoActivo by remember { mutableStateOf(true) }

    var camionX by remember { mutableFloatStateOf(0f) }
    var basuraY by remember { mutableFloatStateOf(0f) }
    var basuraX by remember { mutableFloatStateOf(0f) }
    var basura2Y by remember { mutableFloatStateOf(0f) }
    var basura2X by remember { mutableFloatStateOf(0f) }
    var lataY by remember { mutableFloatStateOf(0f) }
    var lataX by remember { mutableFloatStateOf(0f) }
    var manzanaY by remember { mutableFloatStateOf(0f) }
    var manzanaX by remember { mutableFloatStateOf(0f) }

    var screenWidth by remember { mutableFloatStateOf(0f) }
    var screenHeight by remember { mutableFloatStateOf(0f) }

    var camionWidth by remember { mutableIntStateOf(120) }
    var camionHeight by remember { mutableIntStateOf(120) }

    val offsetYPx = (-50).dp.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(juegoActivo) {
                if (!juegoActivo) {
                    puntos = 0
                    vidas = 3
                    juegoActivo = true
                    basuraY = 0f
                    basura2Y = 0f
                    lataY = 0f
                    manzanaY = 0f
                    if (screenWidth > 0) {
                        basuraX = Random.nextInt(0, (screenWidth - 80).toInt()).toFloat()
                        basura2X = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                        lataX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                        manzanaX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                    }
                }
            }
            .pointerInput(juegoActivo, screenWidth) {
                if (juegoActivo) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val newX = camionX + dragAmount.x
                        camionX = newX.coerceIn(0f, screenWidth - camionWidth)
                    }
                }
            }
            .onGloballyPositioned { coordinates ->
                screenWidth = coordinates.size.width.toFloat()
                screenHeight = coordinates.size.height.toFloat()
                if (camionX == 0f) {
                    camionX = (screenWidth - 120f) / 2
                }
                if (basuraX == 0f && screenWidth > 0) {
                    basuraX = Random.nextInt(0, (screenWidth - 80).toInt()).toFloat()
                    basura2X = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                    lataX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                    manzanaX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }
            }
    ) {
        val camionY = screenHeight - camionHeight.toFloat() + offsetYPx

        Image(
            painter = painterResource(R.drawable.fondo),
            contentDescription = "fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(R.drawable.botella),
            contentDescription = "basura",
            modifier = Modifier
                .size(80.dp)
                .offset { IntOffset(basuraX.toInt(), basuraY.toInt()) }
        )

        Image(
            painter = painterResource(R.drawable.camion),
            contentDescription = "camion",
            modifier = Modifier
                .size(120.dp)
                .offset { IntOffset(camionX.toInt(), camionY.toInt()) }
                .onGloballyPositioned { coordinates ->
                    camionWidth = coordinates.size.width
                    camionHeight = coordinates.size.height
                }
        )

        Image(
            painter = painterResource(R.drawable.cascara),
            contentDescription = "basura2",
            modifier = Modifier
                .size(60.dp)
                .offset { IntOffset(basura2X.toInt(), basura2Y.toInt()) }
        )

        Image(
            painter = painterResource(R.drawable.lata),
            contentDescription = "lata",
            modifier = Modifier
                .size(60.dp)
                .offset { IntOffset(lataX.toInt(), lataY.toInt()) }
        )

        Image(
            painter = painterResource(R.drawable.manzana),
            contentDescription = "manzana",
            modifier = Modifier
                .size(60.dp)
                .offset { IntOffset(manzanaX.toInt(), manzanaY.toInt()) }
        )

        Text(
            text = if (juegoActivo) "Puntos: $puntos  Vidas: $vidas" else "GAME OVER\nToca para reiniciar",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 16.dp, y = 16.dp)
        )

        LaunchedEffect(juegoActivo, screenWidth, screenHeight) {
            if (!juegoActivo || screenWidth == 0f) return@LaunchedEffect

            while (juegoActivo) {
                delay(30)

                lataY += 18
                manzanaY += 22
                basuraY += 20
                basura2Y += 25

                if (basuraY > screenHeight) {
                    basuraY = 0f
                    basuraX = Random.nextInt(0, (screenWidth - 80).toInt()).toFloat()
                }

                if (basura2Y > screenHeight) {
                    basura2Y = 0f
                    basura2X = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }
                if (lataY > screenHeight) {
                    lataY = 0f
                    lataX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }

                if (manzanaY > screenHeight) {
                    manzanaY = 0f
                    manzanaX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }

                if (!juegoActivo) break

                if (camionX < basuraX + 80 &&
                    camionX + camionWidth > basuraX &&
                    camionY < basuraY + 80 &&
                    camionY + camionHeight > basuraY
                ) {
                    puntos++
                    basuraY = 0f
                    basuraX = Random.nextInt(0, (screenWidth - 80).toInt()).toFloat()
                }

                if (!juegoActivo) break

                if (camionX < basura2X + 60 &&
                    camionX + camionWidth > basura2X &&
                    camionY < basura2Y + 60 &&
                    camionY + camionHeight > basura2Y
                ) {
                    vidas--
                    basura2Y = 0f
                    basura2X = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                    if (vidas <= 0) {
                        juegoActivo = false
                    }
                }

                if (!juegoActivo) break

                if (camionX < lataX + 60 &&
                    camionX + camionWidth > lataX &&
                    camionY < lataY + 60 &&
                    camionY + camionHeight > lataY
                ) {
                    puntos++
                    lataY = 0f
                    lataX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }

                if (!juegoActivo) break

                if (camionX < manzanaX + 60 &&
                    camionX + camionWidth > manzanaX &&
                    camionY < manzanaY + 60 &&
                    camionY + camionHeight > manzanaY
                ) {
                    vidas--
                    manzanaY = 0f
                    manzanaX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                    if (vidas <= 0) {
                        juegoActivo = false
                    }
                }
            }
        }
    }
}
