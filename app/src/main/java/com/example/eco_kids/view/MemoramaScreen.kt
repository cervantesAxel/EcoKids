package com.example.eco_kids.view

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eco_kids.R
import com.example.eco_kids.model.MemoryCard
import com.example.eco_kids.viewmodel.GameViewModel
import com.example.eco_kids.viewmodel.MemoramaViewModel
import com.example.eco_kids.viewmodel.UserViewModel
import com.example.eco_kids.viewmodel.WelcomeViewModel

@Composable
fun MemoramaScreen(onGoToGames: () -> Unit,
viewModel: GameViewModel,
                   userViewModel: UserViewModel
) {

    //sonido declaraciones
    val context = LocalContext.current
    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(2).build()
    }

    val soundCorrect = remember {
        soundPool.load(context, R.raw.sonido_acierto2, 1)
    }

    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
        }
    }

    LaunchedEffect(viewModel.isMatch.value) {
        if (viewModel.isMatch.value) {
            soundPool.play(soundCorrect, 1f, 1f, 0, 0, 1f)
        }
    }

    val vibrator = remember {if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }}

    //variable para mostyrar instrucciones
    var showInstructions by remember { mutableStateOf(true) }
    var isNavigatingOut by remember { mutableStateOf(false) }

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
    val memoramaViewModel: MemoramaViewModel = viewModel()
    val bestScore by memoramaViewModel.bestScore.collectAsState(initial = 0)

    if (viewModel.showVictoryDialog && !isNavigatingOut) {
        LaunchedEffect(Unit) {
            memoramaViewModel.finishGame(viewModel.score)
        }
        VictoryDialog(
            score = viewModel.score,
            onRestart = {
                viewModel.setupGame()
            },
            onExit = {
                isNavigatingOut = true // 1. Frenamos el renderizado del diálogo
                onGoToGames()          // 2. Navegamos
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // FONDO
        Image(
            painter = painterResource(id = R.drawable.fondo_pantalla4),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
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


                        // Botón SALIR
                        Button(
                            onClick = {
                                isNavigatingOut = true
                                onGoToGames()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800).copy(0.7f)
                            ),
                            shape = CircleShape,
                            modifier = Modifier.size(50.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        // Botón REINICIAR
                        Button(
                            onClick = { viewModel.setupGame() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF01586C).copy(0.7f)),
                            shape = CircleShape,
                            modifier = Modifier.size(50.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.again),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        // PUNTAJE
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "PUNTOS: ${viewModel.score}",
                                color = Color(0xFF107214),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(
                                    shadow = Shadow(Color.Black, blurRadius = 4f)
                                )
                            )
                            Text(
                                text = "RECORD: $bestScore",
                                color = Color(0xFF01586C),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(
                                    shadow = Shadow(Color.Black, blurRadius = 4f)
                                )
                            )

                        }
                    }
                }

                // Zona de Tablero
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.White.copy(alpha = 0.5f),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(12.dp)
                    ) {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            itemsIndexed(viewModel.cards) { index, card ->
                                if (!card.isStored) {
                                    FlipCardItem(card) {
                                        viewModel.onCardClicked(index)
                                    }
                                } else {
                                    Spacer(modifier = Modifier.size(80.dp))
                                }
                            }
                        }

                        // ================= BOTES =================
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            RecycleBin("Plástico", viewModel.plasticCount, R.drawable.bote_plastico)
                            RecycleBin("Papel", viewModel.paperCount, R.drawable.bote_papel)
                            RecycleBin("Orgánico", viewModel.organicCount, R.drawable.bote_organico)
                        }
                    }
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
                        onGoToGames()
                              },
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
                                    text = "¡Voltea cartas y encuentra los pares correctos!",
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

@Composable
fun FlipCardItem(card: MemoryCard, onClick: () -> Unit) {
    val rotation by animateFloatAsState(
        targetValue = if (card.isFaceUp) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp) // Espaciado extra para que la sombra se vea bien
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 14f * density
            }
            .clickable { onClick() }
    ) {
        if (rotation <= 90f) {
            // --- PARTE TRASERA (BOCA ABAJO) ---
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                border = androidx.compose.foundation.BorderStroke(3.dp, Color(0xFF689F38)), // Borde verde oscuro
                colors = CardDefaults.cardColors(containerColor = Color(0xFF8BC34A))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color(0xFF9CCC65), Color(0xFF7CB342))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Un ícono de reciclaje tenue de fondo para dar textura
                    Icon(
                        painter = painterResource(id = R.drawable.again), // O un ícono de reciclaje
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).alpha(0.2f),
                        tint = Color.White
                    )

                    Text(
                        text = "?",
                        fontSize = 40.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(2f, 4f),
                                blurRadius = 8f
                            )
                        )
                    )
                }
            }
        } else {
            // --- PARTE FRONTAL (BOCA ARRIBA) ---
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFE0E0E0)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = card.imageRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

// --- POPUP VICTORIA (Se mantiene igual) ---
@Composable
fun VictoryDialog(score: Int, onRestart: () -> Unit, onExit: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(Color.White).size(300.dp, 350.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.papup),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(0.3f)
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("¡Felicidades!", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Puntaje:", fontSize = 18.sp)
                Text("$score", fontSize = 48.sp, fontWeight = FontWeight.Black, color = Color(0xFFFF9800))
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onRestart, modifier = Modifier.fillMaxWidth()) { Text("🔄 Volver a Jugar") }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = onExit, modifier = Modifier.fillMaxWidth()) { Text("Salir") }
            }
        }
    }
}

// --- BOTES (Se mantiene igual) ---
@Composable
fun RecycleBin(label: String, count: Int, iconRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.TopEnd) {
            Image(painter = painterResource(id = iconRes), contentDescription = label, modifier = Modifier.size(80.dp))
            if (count > 0) {
                Box(modifier = Modifier.size(24.dp).background(Color.Red, CircleShape), contentAlignment = Alignment.Center) {
                    Text(count.toString(), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, style = TextStyle(shadow = Shadow(Color.Black, blurRadius = 4f)))
    }
}