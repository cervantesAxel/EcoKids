package com.example.eco_kids.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eco_kids.data.GamesDataStore
import com.example.eco_kids.data.UserDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SnakeViewModel (
    application: Application
): AndroidViewModel(application) {
    private val userDataStore = UserDataStore (application)
    private val gamesDataStore = GamesDataStore(application)

    val bestScore = userDataStore.userId
        .flatMapLatest { userId ->
            if (userId == null) {
                kotlinx.coroutines.flow.flowOf(0)
            } else {
                gamesDataStore.getBestScore(userId, "snake")
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun finishGame (score:Int){
        viewModelScope.launch {
            val userId = userDataStore.userId.first() ?: return@launch

            gamesDataStore.saveScore(
                userId = userId,
                gameName = "snake",
                score = score
            )
        }
    }
}