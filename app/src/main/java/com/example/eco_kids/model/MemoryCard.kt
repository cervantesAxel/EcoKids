package com.example.eco_kids.model

import androidx.annotation.DrawableRes

// Definimos los tipos de basura
enum class TrashType {
    PLASTIC, PAPER, ORGANIC
}

data class MemoryCard(
    val id: Int,
    @DrawableRes val imageRes: Int,
    val type: TrashType, // Nuevo: ¿Qué tipo de basura es?
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false,
    val isStored: Boolean = false
)