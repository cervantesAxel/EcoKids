package com.example.eco_kids.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eco_kids.R
import com.example.eco_kids.model.MemoryCard
import com.example.eco_kids.viewmodel.GameViewModel

@Composable
fun MemoramaScreen(onGoToGames: () -> Unit,
               viewModel: GameViewModel = viewModel()) {

    if (viewModel.showVictoryDialog) {
        VictoryDialog(viewModel.score, { viewModel.setupGame() }, { onGoToGames() })
    }

    // CONTENEDOR PRINCIPAL
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. FONDO DEL JUEGO (PAISAJE)
        Image(
            painter = painterResource(id = R.drawable.ic_fondo_inicio),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {

            // --- 2. BARRA SUPERIOR CON SU PROPIO FONDO ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Altura fija para la barra
            ) {
                // A. IMAGEN DE FONDO DE LA BARRA
                // Asegúrate de tener 'barra_fondo.png' o cambia esto por .background(Color...)
                Image(
                    painter = painterResource(id = R.drawable.fondoarriba),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds, // Que llene la barra
                    modifier = Modifier.fillMaxSize()
                )

                // B. BOTONES Y PUNTAJE (Encima del fondo de la barra)
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp), // Ajuste para que no quede pegado a los bordes
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón SALIR
                    Button(
                        onClick = { onGoToGames() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.buttonElevation(4.dp)
                    ) { Text("X", fontWeight = FontWeight.Bold, fontSize = 20.sp) }

                    // Botón REINICIAR
                    Button(
                        onClick = { viewModel.setupGame() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.buttonElevation(4.dp)
                    ) { Text("🔄", fontSize = 24.sp) }

                    // PUNTAJE
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "PUNTOS",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(shadow = Shadow(Color.Black, blurRadius = 4f))
                        )
                        Text(
                            text = "${viewModel.score}",
                            color = Color.Yellow,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            style = TextStyle(shadow = Shadow(Color.Black, blurRadius = 8f))
                        )
                    }
                }
            }

            // --- 3. TABLERO DE CARTAS ---
            Column(modifier = Modifier.weight(1f).padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
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
                                FlipCardItem(card) { viewModel.onCardClicked(index) }
                            } else {
                                Spacer(modifier = Modifier.size(80.dp))
                            }
                        }
                    }
                }
            }

            // --- 4. BOTES (SIN FONDO) ---
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                RecycleBin("Plástico", viewModel.plasticCount, R.drawable.botegris)
                RecycleBin("Papel", viewModel.paperCount, R.drawable.botenaranja)
                RecycleBin("Orgánico", viewModel.organicCount, R.drawable.boteverde)
            }
        }
    }
}

// --- CARTA CON ANIMACIÓN 3D (Se mantiene igual) ---
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
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { onClick() }
    ) {
        if (rotation <= 90f) {
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF8BC34A)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("?", fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = card.imageRes),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp).fillMaxSize()
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
            Image(painter = painterResource(id = iconRes), contentDescription = label, modifier = Modifier.size(70.dp))
            if (count > 0) {
                Box(modifier = Modifier.size(24.dp).background(Color.Red, CircleShape), contentAlignment = Alignment.Center) {
                    Text(count.toString(), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, style = TextStyle(shadow = Shadow(Color.Black, blurRadius = 4f)))
    }
}