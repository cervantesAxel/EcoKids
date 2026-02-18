package com.example.eco_kids.view

import android.R.attr.enabled
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.remote.creation.modifiers.RoundedRectShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eco_kids.R
import com.example.eco_kids.Screen
import com.example.eco_kids.viewmodel.UserViewModel

@Composable
fun GamesScreen (
    navController: NavController,
    userViewModel: UserViewModel
) {
    val name by userViewModel.userName.collectAsState(initial = "")
    val pet by userViewModel.userPet.collectAsState(initial = -1)


    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFFEAE2C7)),
        contentAlignment = Alignment.Center

    ) {
        Image(
            painter = painterResource(R.drawable.ic_fondo_inicio),
            contentDescription = "fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        //aqui va header
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 30.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .drawBehind {

                        val width = size.width
                        val height = size.height

                        val path = Path().apply {

                            // Curva superior
                            moveTo(0f, height * 0.15f)

                            quadraticBezierTo(
                                width / 2,
                                height * 0.35f,
                                width,
                                height * 0.15f
                            )

                            lineTo(width, height * 0.8f)

                            quadraticBezierTo(
                                width / 2,
                                height * 1.05f,
                                0f,
                                height * 0.8f
                            )

                            close()
                        }

                        drawPath(
                            path = path,
                            color = Color(0xFF2E7D32)
                        )
                    }
                    .padding(horizontal = 22.dp),
                contentAlignment = Alignment.CenterStart
            ) {

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

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start) {
                    val selectedPet = mascotas.getOrNull(pet)

                    selectedPet?.let {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            modifier = Modifier.size(70.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(5.dp))

                    Text(
                        text = "Hola, $name!",
                        color = Color.White,
                        fontSize = 22.sp
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GameCard(R.drawable.memorama_banner, "memorama") {
                navController.navigate(Screen.Memorama.route)
            }
            Spacer(modifier = Modifier.height(20.dp))
            GameCard(R.drawable.memorama_banner, "atrapar") {
                navController.navigate(Screen.Memorama.route)
            }
            Spacer(modifier = Modifier.height(20.dp))
            GameCard(R.drawable.memorama_banner, "tercerjuego") {
                navController.navigate(Screen.Memorama.route)
            }
        }
    }
}


@Composable
fun GameCard (imagen: Int, desc: String, onClick: () -> Unit){

    Box(
        modifier = Modifier.fillMaxWidth(0.8f)
            .height(150.dp)
            .padding(top = 16.dp)
            .background(
                shape = RoundedCornerShape(18.dp),
                color = Color.White
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = imagen),
            contentDescription = desc,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

    }
}

