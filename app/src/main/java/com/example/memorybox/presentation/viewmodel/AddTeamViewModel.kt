package com.example.memorybox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.repositories.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTeamViewModel (private val repository: Repository) : ViewModel() {

    fun addTeam(team: Team){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTeam(team)
        }
    }

    fun getUser(): FirebaseUser?{
        return repository.getUser()
    }
}