package com.example.eco_kids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eco_kids.view.ArrastrarGameScreen
import com.example.eco_kids.view.GamesScreen
import com.example.eco_kids.view.MemoramaScreen
import com.example.eco_kids.view.ProfileScreen
import com.example.eco_kids.view.SplashScreen
import com.example.eco_kids.view.WelcomeScreen
import com.example.eco_kids.viewmodel.GameViewModel
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
        val userViewModel: UserViewModel = viewModel()
        val gameViewModel: GameViewModel = viewModel()

        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ) {

            composable(Screen.Splash.route) {
                SplashScreen(
                    userViewModel = userViewModel,
                    onContinueRegister = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    onContinueGames = {
                        navController.navigate(Screen.Games.route){
                            popUpTo(Screen.Games.route){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                WelcomeScreen(
                    onContinueGames = {
                        navController.navigate(Screen.Games.route){
                            popUpTo(Screen.Games.route){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable (Screen.Games.route){
                GamesScreen(
                    onGoToMemorama = {
                    navController.navigate(Screen.Memorama.route){
                        popUpTo(Screen.Memorama.route){
                            inclusive = true
                        }
                    }
                },
                    onGotoArrastrar = {
                        navController.navigate(Screen.Arrastrar.route){
                            popUpTo(Screen.Arrastrar.route){
                                inclusive = true
                            }
                        }
                    },
                    onGoToProfile = {
                        navController.navigate(Screen.Profile.route){
                            popUpTo(Screen.Profile.route){
                                inclusive = true
                            }
                        }
                    },
                    userViewModel = userViewModel)
            }

            composable (Screen.Profile.route) {
                ProfileScreen(
                    userViewModel = userViewModel,
                    onGoToGames = {
                        navController.navigate(Screen.Games.route){
                            popUpTo(Screen.Games.route){
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable (Screen.Memorama.route) {
                MemoramaScreen(
                    userViewModel = userViewModel,
                     onGoToGames = {
                         navController.navigate(Screen.Games.route){
                             popUpTo(Screen.Games.route){
                                 inclusive = true
                             }
                         }
                     },
                    viewModel  = gameViewModel
                 )
            }

            composable (Screen.Arrastrar.route) {
                ArrastrarGameScreen(
                    onGoToGames = {
                        navController.navigate(Screen.Games.route){
                            popUpTo(Screen.Games.route){
                                inclusive = true
                            }
                        }
                    },
                    userViewModel = userViewModel
                )
            }


        }
    }

}