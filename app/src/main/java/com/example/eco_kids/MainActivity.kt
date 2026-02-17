package com.example.eco_kids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eco_kids.data.GamesDataStore
import com.example.eco_kids.data.UserDataStore
import com.example.eco_kids.view.GamesScreen
import com.example.eco_kids.view.NameScreen
import com.example.eco_kids.view.SplashScreen
import com.example.eco_kids.viewmodel.MemoramaViewModel
import com.example.eco_kids.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }

    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ) {

            composable(Screen.Splash.route) {
                val userViewModel: UserViewModel = viewModel()
                SplashScreen(
                    onContinueRegister = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    onContinueGames = {
                        navController.navigate(Screen.Games.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    userViewModel= userViewModel
                )
            }

            composable(Screen.Home.route) {
                NameScreen()
            }

            composable (Screen.Games.route) {
                GamesScreen()
            }

        }
    }

}
