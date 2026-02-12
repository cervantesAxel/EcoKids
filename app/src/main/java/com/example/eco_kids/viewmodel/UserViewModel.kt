package com.example.eco_kids.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eco_kids.data.UserDataStore
import kotlinx.coroutines.launch

class UserViewModel (
    context: Context
): ViewModel(){
    private val userDataStore = UserDataStore (context)

    val userName = userDataStore.userName
    val userPet = userDataStore.userPet

    fun saveUser (name:String, pet:String){
        viewModelScope.launch {
            userDataStore.saveUser(name, pet)
        }
    }

}