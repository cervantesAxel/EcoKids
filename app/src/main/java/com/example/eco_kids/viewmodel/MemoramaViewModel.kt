package com.example.eco_kids.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eco_kids.data.GamesDataStore
import com.example.eco_kids.data.UserDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MemoramaViewModel (
    application: Application
): AndroidViewModel(application) {
    private val userDataStore = UserDataStore (application)
    private val gamesDataStore = GamesDataStore(application)

    val bestScore = MutableStateFlow(0)

    init {
        loadBestScore()
    }

    private fun loadBestScore (){
        viewModelScope.launch {
            val userName = userDataStore.userName.first() ?: return@launch

            gamesDataStore.getBestScore(userName, "memorama")
                .collect { score ->
                    bestScore.value = score
                }
        }
    }

    fun finishGame (score:Int){
        viewModelScope.launch {
            val userName = userDataStore.userName.first() ?: return@launch

            gamesDataStore.saveScore(
                userName= userName,
                gameName = "memorama",
                score = score
            )
        }
    }
}