package com.example.eco_kids.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.eco_kids.R

class WelcomeViewModel : ViewModel() {
    val imagenes = mutableStateListOf(
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

    var nombre by mutableStateOf("")
    private set
    fun onNombreChange(value: String) {
        nombre = value
    }

    var mascotaSeleccionada by mutableStateOf(0)
        private set
    fun onMascotaSeleccionadaChange(index: Int) {
        mascotaSeleccionada = index
    }
}