package com.example.eco_kids

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Games : Screen("games")
    object Memorama : Screen("memorama")
    object Arrastrar : Screen("arrastrar")
    object Profile: Screen("profile")
    object Camion: Screen("camion")
    object Snake: Screen("snake")
}
