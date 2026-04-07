package com.example.eco_kids.view

import android.media.SoundPool
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eco_kids.R
import com.example.eco_kids.viewmodel.SnakeViewModel
import com.example.eco_kids.viewmodel.UserViewModel
import kotlinx.coroutines.delay


@Composable
fun SnakeGame(
    onGoToGames: () -> Unit,
    userViewModel: UserViewModel
) {
    //sonido declaraciones
    val context = LocalContext.current
    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(2).build()
    }

    val soundCorrect = remember {
        soundPool.load(context, R.raw.sonido_snake, 1)
    }

    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
        }
    }

    var soundLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        soundPool.setOnLoadCompleteListener { _, _, status ->
            soundLoaded = status == 0
        }
    }

    //variable para mostrar instrucciones
    var showInstructions by remember { mutableStateOf(true) }
    var isNavigatingOut by remember { mutableStateOf(false) }

    //mascota para mostrar
    val pet by userViewModel.userPet.collectAsState(initial = -1)
    val name by userViewModel.userName.collectAsState(initial = "")
    val snakeViewModel: SnakeViewModel = viewModel()
    val bestScore by snakeViewModel.bestScore.collectAsState()

    val mascotas = listOf(
        R.drawable.mascota_1,
        R.drawable.mascota_2,
        R.drawable.mascota_3,
        R.drawable.mascota_4,
        R.drawable.mascota_5,
        R.drawable.mascota_6,
        R.drawable.mascota_7,
        R.drawable.mascota_8,
        R.drawable.mascota_9
    )


    val selectedPet = mascotas.getOrNull(pet)
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

    LaunchedEffect(gameActive, showInstructions) {
        if (gameActive && !showInstructions) {
            delay(500) //para q no empiece luego luego
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
                    snakeBody.contains(nuevaCabeza)
                ) {
                    gameActive = false
                    showGameOverDialog = true

                    //guarda mejor puntaje al finalizar
                    snakeViewModel.finishGame(puntos)

                    break
                }

                if (nuevaCabeza.x.toInt() == comidaPos.x.toInt() &&
                    nuevaCabeza.y.toInt() == comidaPos.y.toInt()
                ) {

                    if (soundLoaded) {
                        soundPool.play(soundCorrect, 1f, 1f, 1, 0, 1f)
                    }
                    puntos += 10
                    snakeBody = listOf(nuevaCabeza) + snakeBody
                    generarComida(snakeBody)
                } else {
                    snakeBody = listOf(nuevaCabeza) + snakeBody.dropLast(1)
                }
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(gameActive,showInstructions) {
                if (!gameActive || showInstructions) return@pointerInput
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

        val headerHeight = maxHeight * 0.10f
        val maxWidth = this.maxWidth

        Image(
            painter = painterResource(id = R.drawable.fondo_pantalla4),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ZONA DE LA BARRA
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(headerHeight)
                    .padding(top = maxWidth * 0.05f)
                    .shadow(12.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
            ) {

                Image(
                    painter = painterResource(id = R.drawable.barra_games2),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Botón regresar
                    Button(
                        onClick = {

                            isNavigatingOut = true
                            onGoToGames()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9800).copy(0.8f)
                        ),
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    // Puntos
                    Text(
                        text = "Puntos: $puntos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF107214)
                    )

                    // Vidas
                    Text(
                        text = "Récord: $bestScore",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF01586C)
                    )

                }
            }


                    // AKI EMPIEZA EL JUEGO
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp)
                            .aspectRatio(1f)
                            .shadow(12.dp, RoundedCornerShape(16.dp))
                            .background(
                                Color(0xFF012E40).copy(alpha = 0.9f),
                                RoundedCornerShape(16.dp)
                            )
                            .border(5.dp, Color(0xFF2E7D32), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                    ) {

                        val boardWidthPx = with(androidx.compose.ui.platform.LocalDensity.current) {
                            (maxWidth - 32.dp).toPx()
                        }
                        val cellSize = boardWidthPx / columnas

                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val linePaint = Color.White.copy(alpha = 0.1f)

                            for (row in 0 until filas) {
                                for (col in 0 until columnas) {

                                    val isEven = (row + col) % 2 == 0

                                    drawRect(
                                        color = if (isEven)
                                            Color(0xFFAAD751)   //cuadricula verde claro
                                        else
                                            Color(0xFFA2D149),  // aki va un ver más oscuro
                                        topLeft = Offset(
                                            col * cellSize,
                                            row * cellSize
                                        ),
                                        size = Size(cellSize, cellSize)
                                    )
                                }
                            }

                            snakeBody.forEachIndexed { index, segment ->
                                drawRoundRect(
                                    color = if (index == 0) Color(0xFF8BC34A) else Color(0xFF4CAF50),
                                    topLeft = Offset(
                                        segment.x * cellSize + 1.dp.toPx(),
                                        segment.y * cellSize + 1.dp.toPx()
                                    ),
                                    size = androidx.compose.ui.geometry.Size(
                                        cellSize - 2.dp.toPx(),
                                        cellSize - 2.dp.toPx()
                                    )
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
                        )
                    }
                }

        if (showInstructions) {
            AlertDialog(
                onDismissRequest = {  },
                confirmButton = {
                    Button(
                        onClick = {
                            showInstructions = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9800),
                            contentColor = Color.White
                        )
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        androidx.compose.material3.Text("¡Jugar!")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showInstructions = false
                            onGoToGames()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9800),
                            contentColor = Color.White
                        )
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        androidx.compose.material3.Text("Regresar", color = Color.White)
                    }
                },
                title = {
                    androidx.compose.material3.Text("¿Cómo Jugar?")
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Imagen de la mascota
                        selectedPet?.let { petRes ->
                            Card(
                                modifier = Modifier.fillMaxWidth()
                                    .height(100.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFA3DBDE)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Row(modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = petRes),
                                        contentDescription = "Mascota",
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(12.dp)
                                    )

                                    androidx.compose.material3.Text(
                                        text = "¡Recolecta toda la basura y evita chocar con tu cola o las paredes!",
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF01586C),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                },
                containerColor = Color(0xFFFEFBE8),
                shape = RoundedCornerShape(20.dp)
            )
        }
                // 🪟 GAME OVER
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

