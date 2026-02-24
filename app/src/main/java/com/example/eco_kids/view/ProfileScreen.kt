package com.example.eco_kids.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import com.example.eco_kids.R
import com.example.eco_kids.viewmodel.UserViewModel

@Composable
fun ProfileScreen (userViewModel: UserViewModel,
                   onGoToGames: () -> Unit){

    val name by userViewModel.userName.collectAsState(initial = "")
    val pet by userViewModel.userPet.collectAsState(initial = 0)

    var newName by remember { mutableStateOf(name)}

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
    }


}