package com.example.eco_kids.view

import android.content.Context
import android.media.SoundPool
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.eco_kids.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import com.example.eco_kids.viewmodel.UserViewModel
import kotlinx.coroutines.delay

data class Residuo(val drawableId: Int, val tipo: String)

@Composable
fun ArrastrarGameScreen(onGoToGames: () -> Unit,
                        userViewModel: UserViewModel) {

    //sonido
    val context = LocalContext.current
    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(2).build()
    }

    val soundCorrect = remember {
        soundPool.load(context, R.raw.sonido_acierto, 1)
    }

    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
        }
    }

    val vibrator = remember {if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }}

    //variable para mostrar instrucciones
    var showInstructions by remember { mutableStateOf(true) }

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


    var puntos by remember { mutableStateOf(0) }
    var vidas by remember { mutableStateOf(3) }
    var isGameOver by remember { mutableStateOf(false) }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(-300f) }

    var residuoBounds by remember { mutableStateOf<Rect?>(null) }
    var boteOrganicoBounds by remember { mutableStateOf<Rect?>(null) }
    var botePlasticoBounds by remember { mutableStateOf<Rect?>(null) }
    var botePapelBounds by remember { mutableStateOf<Rect?>(null) }

    val residuos = listOf(
        Residuo(R.drawable.org_hoja,"organico"),
        Residuo(R.drawable.org_banano,"organico"),
        Residuo(R.drawable.org_lechuga,"organico"),
        Residuo(R.drawable.org_manzana,"organico"),
        Residuo(R.drawable.org_cascara,"organico"),


        Residuo(R.drawable.pap_cafe,"papel"),
        Residuo(R.drawable.pap_caja,"papel"),
        Residuo(R.drawable.pap_libros,"papel"),
        Residuo(R.drawable.pap_cereal,"papel"),
        Residuo(R.drawable.pap_sobre,"papel"),

        Residuo(R.drawable.pet_botella,"plastico"),
        Residuo(R.drawable.pet_vaso, "plastico"),
        Residuo(R.drawable.lata, "plastico"),
        Residuo(R.drawable.pet_tapas, "plastico"),
        Residuo(R.drawable.pet_cuchara, "plastico")

        )

    var currentResiduo by remember { mutableStateOf(residuos.random()) }

    fun vibrar (duration: Long){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration, VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(duration)
        }
    }

    fun resetResiduo() {
        var nuevo: Residuo
        do {
            nuevo = residuos.random()
        } while (nuevo == currentResiduo)

        currentResiduo = nuevo
        offsetX = 0f
        offsetY = -300f
    }

    fun resetGame() {
        puntos = 0
        vidas = 3
        isGameOver = false
        resetResiduo()
    }

    fun zonaDeCaptura(bounds: Rect?): Rect? {
        return bounds?.inflate(60f)
    }

    fun checkCollision() {
        val residuo = residuoBounds ?: return

        when {
            zonaDeCaptura(boteOrganicoBounds)?.overlaps(residuo) == true -> {
                if (currentResiduo.tipo == "organico")   {
                    puntos += 10
                    soundPool.play(soundCorrect, 1f, 1f, 0, 0, 1f)
                    vibrar(50)
            } else {
                vidas--
                    vibrar(200)

                }
            resetResiduo()
            }

            zonaDeCaptura(botePlasticoBounds)?.overlaps(residuo) == true -> {
                if (currentResiduo.tipo == "plastico") {
                    puntos += 10
                    soundPool.play(soundCorrect, 1f, 1f, 0, 0, 1f)
                    vibrar(50)
                } else {
                    vidas--
                    vibrar(200)
                }
                resetResiduo()
            }

            zonaDeCaptura(botePapelBounds)?.overlaps(residuo) == true -> {
                if (currentResiduo.tipo == "papel") {
                    puntos += 10
                    soundPool.play(soundCorrect, 1f, 1f, 0, 0, 1f)
                    vibrar(50)
                } else {
                    vidas--
                    vibrar(200)
                }
                resetResiduo()

            }
        }

        if (vidas <= 0) isGameOver = true
    }

    // Lógica de la caida del objeto    
    LaunchedEffect(currentResiduo, isGameOver, showInstructions) {
        if (!isGameOver && !showInstructions) {
            while (offsetY < 1500f && !isGameOver) {
                delay(16L)
                offsetY += 8f
                checkCollision()
            }

            if (!isGameOver) {
                vidas--
                if (vidas <= 0) isGameOver = true
                else resetResiduo()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.ic_fondo_inicio),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

            val maxWidth = this.maxWidth
            val headerHeight = maxHeight * 0.10f

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Header con puntos y vidas
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .height(headerHeight)
                        .padding(top = maxWidth * 0.050f)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clip(RoundedCornerShape(20.dp))
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.barra_games2),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // Botón SALIR
                    Button(
                        onClick = { onGoToGames() },
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

                    Text(
                        text = "Puntos: $puntos",
                        fontSize = 20.sp,
                        color = Color(0xFF107214),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 20.dp)
                    )

                    Text(
                        text = "Vidas: $vidas",
                        fontSize = 20.sp,
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (!isGameOver) {
                    Image(
                        painter = painterResource(id = currentResiduo.drawableId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                            .onGloballyPositioned {
                                residuoBounds = it.boundsInRoot()
                            }
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    offsetX += dragAmount.x
                                    //mover hacia abajo ->
                                    offsetY += dragAmount.y
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.padding(bottom = 32.dp),
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
                        painter = painterResource(id = R.drawable.bote_plastico),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .onGloballyPositioned {
                                botePlasticoBounds = it.boundsInRoot()
                            }
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    Image(
                        painter = painterResource(id = R.drawable.bote_papel),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .onGloballyPositioned {
                                botePapelBounds = it.boundsInRoot()
                            }
                    )
                }
            }
        }

        if (showInstructions) {
            AlertDialog(
                onDismissRequest = { onGoToGames() },
                confirmButton = {
                    Button(
                        onClick = {
                            showInstructions = false
                            resetResiduo()
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
                        onClick = {onGoToGames()},
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
                                            .padding(10.dp)
                                    )

                                    Text(
                                        "¡Arrastra el residuo hacia el bote correcto!",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,
                                        color = Color(0xFF01586C)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tipos de residuos
                            ResiduoRow("🟢", "Orgánico", Color(0xFF4CAF50))
                            ResiduoRow("🟠", "Plástico", Color(0xFFFF9800))
                            ResiduoRow("⚫", "Papel", Color(0xFF607D8B))

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            "¡Gana puntos y no pierdas tus vidas!",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        )
                    }
                },
                containerColor = Color(0xFFFEFBE8),
                shape = RoundedCornerShape(20.dp)
            )
        }


        // Dialogo final del juego
        if (isGameOver) {
            VictoryDialog(
                score = puntos,
                onRestart = { resetGame() },
                onExit = { onGoToGames() }
            )
        }
    }
}

@Composable
private fun ResiduoRow(emoji: String, texto: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyLarge,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}