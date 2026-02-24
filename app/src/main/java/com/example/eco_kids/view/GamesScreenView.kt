package com.example.eco_kids.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_kids.R
import com.example.eco_kids.viewmodel.UserViewModel

@Composable
fun GamesScreen (
    onGoToMemorama: () -> Unit,
    onGotoArrastrar: () -> Unit,
    onGoToProfile: () -> Unit,
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
            contentScale = ContentScale.Crop
        )

        //aqui va header
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val maxWidth = this.maxWidth
            val maxHeight = this.maxHeight

            val headerHeight = maxHeight * 0.18f
            val petSize = maxWidth * 0.18f
            val textSize = (maxWidth.value * 0.06f).sp

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(headerHeight)
                        .padding(top = maxHeight * 0.075f)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = Color.Black.copy(alpha = 0.2f),
                            spotColor = Color.Black.copy(alpha = 0.15f)
                        )
                        .clip(RoundedCornerShape(20.dp))
                ) {

                    Image(
                        painter = painterResource(R.drawable.fondo_barra2),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = maxWidth * 0.02f)
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

                        val selectedPet = mascotas.getOrNull(pet)

                        selectedPet?.let {
                            Image(
                                painter = painterResource(id = it),
                                contentDescription = null,
                                modifier = Modifier.size(petSize)
                                    .clickable{
                                        onGoToProfile()
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.width(maxWidth * 0.06f))

                        Text(
                            text = "Hola, $name!",
                            color = Color(0xFF107214),
                            fontSize = textSize,
                            fontWeight = FontWeight.Bold
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
                    text = "Mis Juegos",
                    color = Color(0xFF01586C),
                    fontSize = (maxWidth.value * 0.07f).sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(maxHeight * 0.04f))

                GameCard(R.drawable.memorama_banner, "Memorama") {
                    onGoToMemorama()
                }
                Spacer(modifier = Modifier.height(20.dp))
                GameCard(R.drawable.atrapabasura_banner, "Atrapa Basura") {
                    onGotoArrastrar()
                }
                Spacer(modifier = Modifier.height(20.dp))
                GameCard(R.drawable.banner_camion, "Camion recolector") {
                    onGoToMemorama() //navegacion pendiente
                }

            }
        }
    }
}


@Composable
fun GameCard(
    imagen: Int,
    descripcion: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(100.dp)
            .graphicsLayer {
                rotationZ = -2f
            }
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFFEFBE8))
            .clickable { onClick() }
            .padding(6.dp),
            ) {

        Image(
            painter = painterResource(id = imagen),
            contentDescription = descripcion,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )


    }
}




