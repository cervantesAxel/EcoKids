package com.example.eco_kids.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eco_kids.R
import com.example.eco_kids.viewmodel.UserViewModel
import kotlinx.coroutines.delay


@Composable
fun SnakeGame(
    onGoToGames: () -> Unit,
    userViewModel: UserViewModel
) {
    val tiposDeResiduos = listOf(
        R.drawable.pet_botella,
        R.drawable.lata
    )
    var direccionProcesada by remember { mutableStateOf(Offset(0f, -1f)) }
    val filas = 12
    val columnas = 12
    var snakeBody by remember {
        mutableStateOf(listOf(Offset(7f, 7f), Offset(7f, 8f), Offset(7f, 9f)))
    }
    var direccion by remember { mutableStateOf(Offset(0f, -1f)) }
    var gameActive by remember { mutableStateOf(true) }
    var showGameOverDialog by remember { mutableStateOf(false) }
    var puntos by remember { mutableIntStateOf(0) }

    var comidaPos by remember { mutableStateOf(Offset(5f, 5f)) }
    var currentResiduoIcon by remember { mutableIntStateOf(tiposDeResiduos.random()) }

    fun generarComida(snakeBody: List<Offset>) {
        var nuevaPos: Offset
        do {
            nuevaPos = Offset(
                (0 until columnas).random().toFloat(),
                (0 until filas).random().toFloat()
            )
        } while (snakeBody.contains(nuevaPos))

        comidaPos = nuevaPos
        currentResiduoIcon = tiposDeResiduos.random()
    }

    LaunchedEffect(gameActive) {
        while (gameActive) {
            delay(200) //Para cambiar la velocidad de la serpiente IRIS ALEXIA
            direccionProcesada = direccion

            val cabezaActual = snakeBody.first()
            val nuevaCabeza = Offset(
                x = cabezaActual.x + direccion.x,
                y = cabezaActual.y + direccion.y
            )

            if (nuevaCabeza.x < 0 || nuevaCabeza.x >= columnas ||
                nuevaCabeza.y < 0 || nuevaCabeza.y >= filas ||
                snakeBody.contains(nuevaCabeza)) {
                gameActive = false
                showGameOverDialog = true
                break
            }

            if (nuevaCabeza.x.toInt() == comidaPos.x.toInt() &&
                nuevaCabeza.y.toInt() == comidaPos.y.toInt()) {

                puntos += 10
                snakeBody = listOf(nuevaCabeza) + snakeBody
                generarComida(snakeBody)
            } else {
                snakeBody = listOf(nuevaCabeza) + snakeBody.dropLast(1)
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(Color.Black)
            .pointerInput(gameActive) {
                if (!gameActive) return@pointerInput
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val (x, y) = dragAmount
                    if (kotlin.math.abs(x) > kotlin.math.abs(y)) {
                        if (x > 0 && direccionProcesada.x == 0f) direccion = Offset(1f, 0f)
                        else if (x < 0 && direccionProcesada.x == 0f) direccion = Offset(-1f, 0f)
                    } else {
                        if (y > 0 && direccionProcesada.y == 0f) direccion = Offset(0f, 1f)
                        else if (y < 0 && direccionProcesada.y == 0f) direccion = Offset(0f, -1f)
                    }
                }
            }
    ) {
        val totalMaxWidth = maxWidth

        Image(
            painter = painterResource(id = R.drawable.fondo_pantalla4),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .aspectRatio(1f)
                .shadow(12.dp, RoundedCornerShape(16.dp))
                .background(Color(0xFF012E40).copy(alpha = 0.9f), RoundedCornerShape(16.dp)) //CAMBIAR ESTO PARA EL COLOR IRIS ALEXIA
                .clip(RoundedCornerShape(16.dp))

        ) {
            val boardWidthPx = with(androidx.compose.ui.platform.LocalDensity.current) {
                (totalMaxWidth - 32.dp).toPx()
            }
            val cellSize = boardWidthPx / columnas

            Canvas(modifier = Modifier.fillMaxSize()) {
                val linePaint = Color.White.copy(alpha = 0.1f)
                for (i in 0..columnas) {
                    drawLine(linePaint, Offset(i * cellSize, 0f), Offset(i * cellSize, size.height), 1.dp.toPx())
                }
                for (i in 0..filas) {
                    drawLine(linePaint, Offset(0f, i * cellSize), Offset(size.width, i * cellSize), 1.dp.toPx())
                }

                snakeBody.forEachIndexed { index, segment ->
                    drawRect(
                        color = if (index == 0) Color(0xFF8BC34A) else Color(0xFF4CAF50),
                        topLeft = Offset(segment.x * cellSize + 1.dp.toPx(), segment.y * cellSize + 1.dp.toPx()),
                        size = androidx.compose.ui.geometry.Size(cellSize - 2.dp.toPx(), cellSize - 2.dp.toPx())
                    )
                }
            }

            Image(
                painter = painterResource(id = currentResiduoIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(with(androidx.compose.ui.platform.LocalDensity.current) { cellSize.toDp() })
                    .offset(
                        x = with(androidx.compose.ui.platform.LocalDensity.current) { (comidaPos.x * cellSize).toDp() },
                        y = with(androidx.compose.ui.platform.LocalDensity.current) { (comidaPos.y * cellSize).toDp() }
                    )
                    .padding(2.dp)
            )
        }

        if (showGameOverDialog) {
            VictoryDialog(
                score = puntos,
                onRestart = {
                    snakeBody = listOf(Offset(7f, 7f), Offset(7f, 8f), Offset(7f, 9f))
                    direccion = Offset(0f, -1f)
                    puntos = 0
                    showGameOverDialog = false
                    gameActive = true
                },
                onExit = {
                    showGameOverDialog = false
                    onGoToGames()
                }
            )
        }
    }
}