package com.example.eco_kids.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_kids.R
import com.example.eco_kids.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CamionScreen(
    onGoToGames: () -> Unit,
    userViewModel: UserViewModel
) {

    //variable para mostyrar instrucciones
    var showInstructions by remember { mutableStateOf(true) }
    var velocidad by remember { mutableFloatStateOf(10f) }
    var frames by remember { mutableIntStateOf(0) }

    //mascota para mostrar
    val pet by userViewModel.userPet.collectAsState(initial = -1)
    val name by userViewModel.userName.collectAsState(initial = "")

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
    var puntos by remember { mutableIntStateOf(0) }
    var vidas by remember { mutableIntStateOf(3) }
    var juegoActivo by remember { mutableStateOf(true) }
    var showGameOverDialog by remember { mutableStateOf(false) }

    var camionX by remember { mutableFloatStateOf(0f) }
    var basuraY by remember { mutableFloatStateOf(0f) }
    var basuraX by remember { mutableFloatStateOf(0f) }
    var basura2Y by remember { mutableFloatStateOf(-200f) }
    var basura2X by remember { mutableFloatStateOf(0f) }
    var lataY by remember { mutableFloatStateOf(-400f) }
    var lataX by remember { mutableFloatStateOf(0f) }
    var manzanaY by remember { mutableFloatStateOf(-600f) }
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
            painter = painterResource(R.drawable.fondo_carretera2   ),
            contentDescription = "fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(R.drawable.pet_botella),
            contentDescription = "basura",
            modifier = Modifier
                .size(80.dp)
                .offset { IntOffset(basuraX.toInt(), basuraY.toInt()) }
        )

        Image(
            painter = painterResource(R.drawable.camion4),
            contentDescription = "camion",
            modifier = Modifier
                .size(140.dp)
                .offset { IntOffset(camionX.toInt(), camionY.toInt()) }
                .onGloballyPositioned { coordinates ->
                    camionWidth = coordinates.size.width
                    camionHeight = coordinates.size.height
                }
        )

        Image(
            painter = painterResource(R.drawable.org_banano),
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
            painter = painterResource(R.drawable.org_manzana),
            contentDescription = "manzana",
            modifier = Modifier
                .size(60.dp)
                .offset { IntOffset(manzanaX.toInt(), manzanaY.toInt()) }
        )
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val maxWidth = this.maxWidth
            val headerHeight = maxHeight * 0.10f

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .height(headerHeight)
                        .padding(top = maxWidth * 0.050f)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = Color.Black.copy(alpha = 0.2f),
                            spotColor = Color.Black.copy(alpha = 0.15f)
                        )
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.barra_games2),
                        contentDescription = "Imagen barra",
                        modifier = Modifier.fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón regresar
                        Button(
                            onClick = { onGoToGames() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800).copy(0.8f)
                            ),
                            shape = CircleShape,
                            modifier = Modifier.size(45.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
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
                            text = "Vidas: $vidas",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF01586C)
                        )
                    }
                }


        LaunchedEffect(juegoActivo, screenWidth, screenHeight, showInstructions) {
            if (!juegoActivo || screenWidth == 0f || showInstructions) return@LaunchedEffect

            while (juegoActivo && !showInstructions) {
                delay(50)

                frames++
                if (frames % 100 == 0) { // Cada 100 ciclos (aprox 5 seg), aumenta la velocidad
                    velocidad += 1f
                }

                lataY += velocidad
                manzanaY += velocidad + 1 // La manzana sigue siendo un poco más rápida
                basuraY += velocidad
                basura2Y += velocidad + 1

                if (basuraY > screenHeight) {
                    basuraY = -200f
                    basuraX = Random.nextInt(0, (screenWidth - 80).toInt()).toFloat()
                }

                if (basura2Y > screenHeight) {
                    basura2Y = -400f
                    basura2X = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }
                if (lataY > screenHeight) {
                    lataY = -600f
                    lataX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }

                if (manzanaY > screenHeight) {
                    manzanaY = -800f
                    manzanaX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }

                if (!juegoActivo) break

                if (camionX < basuraX + 80 &&
                    camionX + camionWidth > basuraX &&
                    camionY < basuraY + 80 &&
                    camionY + camionHeight > basuraY
                ) {
                    puntos++
                    basuraY = -200f
                    basuraX = Random.nextInt(0, (screenWidth - 80).toInt()).toFloat()
                }

                if (!juegoActivo) break

                if (camionX < basura2X + 60 &&
                    camionX + camionWidth > basura2X &&
                    camionY < basura2Y + 60 &&
                    camionY + camionHeight > basura2Y
                ) {
                    vidas--
                    basura2Y = -400f
                    basura2X = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                    if (vidas <= 0) {
                        juegoActivo = false
                        showGameOverDialog = true
                    }
                }

                if (!juegoActivo) break

                if (camionX < lataX + 60 &&
                    camionX + camionWidth > lataX &&
                    camionY < lataY + 60 &&
                    camionY + camionHeight > lataY
                ) {
                    puntos++
                    lataY = -600f
                    lataX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                }

                if (!juegoActivo) break

                if (camionX < manzanaX + 60 &&
                    camionX + camionWidth > manzanaX &&
                    camionY < manzanaY + 60 &&
                    camionY + camionHeight > manzanaY
                ) {
                    vidas--
                    manzanaY = -800f
                    manzanaX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                    if (vidas <= 0) {
                        juegoActivo = false
                        showGameOverDialog = true
                    }
                }
            }
        }

        if (showGameOverDialog) {
            VictoryDialog(
                score = puntos,
                onRestart = {
                    puntos = 0
                    vidas = 3
                    juegoActivo = true
                    showGameOverDialog = false
                    basuraY = 0f
                    basura2Y = -200f
                    lataY = -400f
                    manzanaY = -600f
                    if (screenWidth > 0) {
                        basuraX = Random.nextInt(0, (screenWidth - 80).toInt()).toFloat()
                        basura2X = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                        lataX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                        manzanaX = Random.nextInt(0, (screenWidth - 60).toInt()).toFloat()
                    }
                },
                onExit = {
                    showGameOverDialog = false
                    onGoToGames()
                }
            )
        }
    }
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
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("¡Jugar!")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showInstructions = false
                        onGoToGames()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Regresar", color = Color.White)
                }
            },
            title = {
                Text("¿Cómo Jugar?")
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

                                Text(
                                    text = "¡Recolecta la basura reciclable y evita los desechos!",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF01586C)
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
}
