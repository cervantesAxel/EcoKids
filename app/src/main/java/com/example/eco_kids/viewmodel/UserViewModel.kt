package com.example.eco_kids.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eco_kids.data.UserDataStore
import kotlinx.coroutines.launch

class UserViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val userDataStore = UserDataStore (application)

    val userName = userDataStore.userName
    val userPet = userDataStore.userPet

    fun saveUser (name:String, pet: Int){
        viewModelScope.launch {
            userDataStore.saveUser(name, pet)
        }
    }

    private val max_name = 10
    fun setName (newName: String){
        if(newName.length <= max_name) {
            viewModelScope.launch {
                userDataStore.setName(newName)
            }
        }
    }

    fun setPet (newPet: Int){
        viewModelScope.launch {
            userDataStore.setPet(newPet)
        }
    }

}