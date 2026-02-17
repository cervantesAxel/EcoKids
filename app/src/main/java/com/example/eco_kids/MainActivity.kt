package com.example.eco_kids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// Asegúrate de que estos imports coincidan con tus carpetas reales
import com.example.eco_kids.view.GameScreen
import com.example.eco_kids.view.NameScreen
import com.example.eco_kids.view.SplashScreen
import com.example.eco_kids.view.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {

            }
        }
    }
}