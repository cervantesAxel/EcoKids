package com.example.eco_kids.view

import android.icu.number.Scale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.eco_kids.R
import com.example.eco_kids.ui.partials.TextField
import com.example.eco_kids.viewmodel.UserViewModel

@Composable
fun ProfileScreen (userViewModel: UserViewModel,
                   onGoToGames: () -> Unit) {

    var confirmDialog by remember { mutableStateOf(false) }

    val name by userViewModel.userName.collectAsState(initial = "")
    val pet by userViewModel.userPet.collectAsState(initial = 0)

    var newName by remember { mutableStateOf("") }
    var selectedPet by remember { mutableStateOf(pet) }

    LaunchedEffect (pet) {
        selectedPet = pet
    }
    LaunchedEffect(name) {
        newName = name ?: ""
    }

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

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFFEAE2C7)),
        contentAlignment = Alignment.Center

    ) {
        Image(
            painter = painterResource(R.drawable.ic_fondo_inicio),
            contentDescription = "fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val maxWidth = this.maxWidth
            val maxHeight = this.maxHeight
            val headerHeight = maxHeight * 0.10f

            val textSize = (maxWidth.value * 0.08f).sp

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

                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {

                        // Botón SALIR
                        Button(
                            onClick = { onGoToGames() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800).copy(0.7f)
                            ),
                            shape = CircleShape,
                            modifier = Modifier.size(50.dp)
                                .align(Alignment.CenterStart),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Text(
                            text = "Mi Perfil",
                            color = Color(0xFF107214),
                            fontSize = textSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }

                }
            }


            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Text(
                        text = "Escribe tu nuevo nombre",
                        color = Color(0xFF01586C),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = newName,
                    onValueChange = {
                        newName = it
                    },
                    placeholder = "Escribe tu nombre",
                    colorContainer = 0xFFFAF1D0,
                    colorBorder = 0xFF01586C
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Escoge tu mascota!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color (0xFF01586C)
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyVerticalGrid (
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth(0.9f)
                        .height(maxHeight*0.45f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(mascotas) { index, petRes ->
                        val isSelected = index == selectedPet


                        Card(
                            modifier = Modifier.aspectRatio(1f)
                                .shadow(
                                    elevation = if (isSelected) 12.dp else 4.dp,
                                        shape = RoundedCornerShape(16.dp)
                                )
                                .clip(RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFFFCE899) else Color(0xFFFEFBE8)
                            ), onClick = {
                                selectedPet = index
                            }
                        ) {
                            Image(
                                painter = painterResource(id = petRes),
                                contentDescription = "Mascota",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(10.dp),
                                    contentScale = ContentScale.Fit
                            )

                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6CB808)
                    ),
                    onClick = {
                        if (newName.isNotBlank()) {
                            confirmDialog = true
                        }
                    }
                ) {
                    Text(text = "Guardar Cambios")
                }

                if (confirmDialog){
                    AlertDialog(
                        onDismissRequest = {confirmDialog = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    confirmDialog = false
                                    userViewModel.setName(newName)
                                    userViewModel.setPet(selectedPet)
                                    onGoToGames()
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
                                Text("Sí, guardar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {confirmDialog = false},
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
                                Text("Cancelar", color = Color.White)
                            }
                        },
                        title = {
                            Text("Confirmar Cambios")
                        },
                        text = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Imagen de la mascota
                                val petRes = mascotas[selectedPet]

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
                                                text = "¿Seguro que quieres confirmar los cambios realizados?",
                                                style = MaterialTheme.typography.bodyLarge,
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF01586C)
                                            )
                                        }
                                    }
                                }
                        },
                        containerColor = Color(0xFFFEFBE8),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
        }

        }


    }
}
