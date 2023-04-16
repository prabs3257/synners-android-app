package com.example.memorybox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.repositories.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTeamViewModel (private val repository: Repository) : ViewModel() {

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }
    fun addTeam(team: Team){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repository.addTeam(team)
        }
    }

    fun getUser(): FirebaseUser?{
        return repository.getUser()
    }
}