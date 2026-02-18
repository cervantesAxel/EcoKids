package com.example.eco_kids.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight

data class Residuo(val drawableId: Int, val tipo: String)

@Composable
fun GameScreenView() {

    val activity = LocalContext.current as Activity

    var puntos by remember { mutableStateOf(0) }
    var vidas by remember { mutableStateOf(3) }
    var isGameOver by remember { mutableStateOf(false) }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    var residuoBounds by remember { mutableStateOf<Rect?>(null) }
    var boteOrganicoBounds by remember { mutableStateOf<Rect?>(null) }
    var boteReciclableBounds by remember { mutableStateOf<Rect?>(null) }
    var boteInorganicoBounds by remember { mutableStateOf<Rect?>(null) }

    val residuos = listOf(
        Residuo(R.drawable.manzana, "organico"),
        Residuo(R.drawable.plastico, "reciclable"),
        Residuo(R.drawable.vidrio, "inorganico"),
        Residuo(R.drawable.banana, "organico"),
        Residuo(R.drawable.lata, "reciclable"),

        )

    var currentResiduo by remember { mutableStateOf(residuos.random()) }

    fun resetResiduo() {
        currentResiduo = residuos.random()
        offsetX = 0f
        offsetY = 0f
    }

    fun resetGame() {
        puntos = 0
        vidas = 3
        isGameOver = false
        resetResiduo()
    }

    fun zonaDeCaptura(bounds: Rect?): Rect? {
        return bounds?.let {
            Rect(
                left = it.left - 60f,
                right = it.right + 60f,
                top = it.top - 120f,
                bottom = it.bottom + 40f
            )
        }
    }

    fun checkCollision() {

        val residuo = residuoBounds ?: return

        val organico = zonaDeCaptura(boteOrganicoBounds)
        val reciclable = zonaDeCaptura(boteReciclableBounds)
        val inorganico = zonaDeCaptura(boteInorganicoBounds)

        when {
            organico?.overlaps(residuo) == true -> {
                if (currentResiduo.tipo == "organico") puntos += 10 else vidas--
                resetResiduo()
            }

            reciclable?.overlaps(residuo) == true -> {
                if (currentResiduo.tipo == "reciclable") puntos += 10 else vidas--
                resetResiduo()
            }

            inorganico?.overlaps(residuo) == true -> {
                if (currentResiduo.tipo == "inorganico") puntos += 10 else vidas--
                resetResiduo()
            }

            else -> return
        }

        if (vidas <= 0) {
            isGameOver = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.ic_fondo_inicio),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Puntos: $puntos",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        Text(
            text = "Vidas: $vidas",
            fontSize = 20.sp,
            color = Color.Red,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )

        if (!isGameOver) {

            Image(
                painter = painterResource(id = currentResiduo.drawableId),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                    .onGloballyPositioned {
                        residuoBounds = it.boundsInRoot()
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = { checkCollision() },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        )
                    }
                    .align(Alignment.Center)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Image(
                painter = painterResource(id = R.drawable.bote_organico),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .onGloballyPositioned {
                        boteOrganicoBounds = it.boundsInRoot()
                    }
            )

            Spacer(modifier = Modifier.width(24.dp))

            Image(
                painter = painterResource(id = R.drawable.bote_reciclable),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .onGloballyPositioned {
                        boteReciclableBounds = it.boundsInRoot()
                    }
            )

            Spacer(modifier = Modifier.width(24.dp))

            Image(
                painter = painterResource(id = R.drawable.bote_inorganico),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .onGloballyPositioned {
                        boteInorganicoBounds = it.boundsInRoot()
                    }
            )
        }

        if (isGameOver) {
            VictoryDialog(
                score = puntos,
                onRestart = { resetGame() },
                onExit = { activity.finish() }
            )
        }
    }
}

@Composable
fun VictoryDialog(score: Int, onRestart: () -> Unit, onExit: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .size(300.dp, 350.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.papup),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.3f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "¡Juego Terminado!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Puntaje Final:", fontSize = 18.sp, color = Color(0xFFFF0000))

                Text(
                    "$score",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFF9800)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onRestart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("🔄 Volver a Jugar")
                }

                Spacer(modifier = Modifier.height(8.dp))

                /* OutlinedButton(
                     onClick = onExit,
                     modifier = Modifier.fillMaxWidth()
                 ) {
                     Text("Salir")
                 }*/
            }
        }
    }
}
