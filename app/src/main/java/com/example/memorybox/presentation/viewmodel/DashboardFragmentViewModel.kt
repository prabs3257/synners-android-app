package com.example.memorybox.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.repositories.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragmentViewModel(private val repository: Repository) : ViewModel()  {

    private var teamLiveData: MutableLiveData<List<Team>> = repository.getTeamsLiveData()

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }
    fun getTeamByUserId(googleId:String){
        Log.d("google login", "sdeweehihiihihi")
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repository.getTeamsByUserId(googleId)
        }
    }

    fun getTeamLiveData() : MutableLiveData<List<Team>> {
        return teamLiveData
    }
    fun logout(){
        repository.logout()
    }

    fun getUser(): FirebaseUser?{
        return repository.getUser()
    }


}