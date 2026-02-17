package com.example.eco_kids.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_kids.R
import com.example.eco_kids.ui.theme.CurvedTopShape
import com.example.eco_kids.ui.partials.TextField
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eco_kids.ui.partials.MascotaCarousel
import com.example.eco_kids.viewmodel.WelcomeViewModel

@Composable
fun WelcomeScreen() {
    val welcomeViewModel: WelcomeViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_fondo_inicio),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 36.dp,
                    top = 175.dp,
                    end = 36.dp,
                    bottom = 250.dp
                )
                .clip(CurvedTopShape)
                .background(Color(0xFFFEFBE8))
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 0.dp,
                        top = 20.dp,
                        end = 0.dp,
                        bottom = 20.dp
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "¡Bienvenido!",
                        color = Color(0xFF01586C),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
                HorizontalDivider(
                    color = Color(0xFF01586C),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "¿Cuál es tu nombre?",
                        color = Color(0xFF01586C),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }
                TextField(
                    value = welcomeViewModel.nombre,
                    onValueChange = { welcomeViewModel.onNombreChange(it) },
                    placeholder = "Escribe tu nombre"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Elige tu mascota:",
                        color = Color(0xFF01586C),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(top = 22.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MascotaCarousel(viewModel = welcomeViewModel)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
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
                            println(welcomeViewModel.nombre)
                        }
                    ) {
                        Text(text = "Continuar")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    WelcomeScreen()
}

