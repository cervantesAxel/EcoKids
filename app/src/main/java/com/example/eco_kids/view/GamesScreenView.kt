package com.example.eco_kids.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_kids.R
import com.example.eco_kids.viewmodel.UserViewModel

@Composable
fun GamesScreen (
    userViewModel: UserViewModel
){
    val name by userViewModel.userName.collectAsState(initial = "")
    val pet by userViewModel.userPet.collectAsState(initial = -1)


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

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Hola, $name",
                fontSize = 22.sp
            )

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
                    modifier = Modifier.size(120.dp)
                )
            }

            Box( modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
                .background(Color.White),
                contentAlignment = Alignment.Center){

                //pendiente de poner esta barra
            }

        }
        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            GameCard(R.drawable.ic_logo_inicio)
            Spacer(modifier = Modifier.height(20.dp))
            GameCard(R.drawable.ic_logo_inicio)
            Spacer(modifier = Modifier.height(20.dp))
            GameCard(R.drawable.ic_logo_inicio)

        }
    }
}

@Composable
fun GameCard (imagen: Int){

    Box(
        modifier = Modifier.fillMaxWidth(0.8f)
            .height(150.dp)
            .padding(top = 16.dp)
            .background(
                shape = RoundedCornerShape(18.dp),
                color = Color.White
            ),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_fondo_inicio),
            contentDescription = "memorama",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

    }
}