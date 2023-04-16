package com.example.memorybox.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Competition
import com.example.memorybox.data.models.Team
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamListViewModel(private val repository: Repository) : ViewModel() {


    private var teamsLiveData: MutableLiveData<List<Team>> = repository.getTeamsLiveData()

//    fun getCompetitions(){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getCompetitions()
//        }
//    }

    fun getTeamsLiveData() : MutableLiveData<List<Team>> {
        return teamsLiveData
    }




//    fun getUserById(id:String){
//        Log.d("google login", "sdeweehihiihihi")
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getUserById(id)
//        }
//    }

    fun getTeamsByComp(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTeamsByComp(id)
        }
    }



//    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
//        throwable.printStackTrace()
//    }
//    fun addUser(user: User){
//        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
//            repository.addUser(user)
//        }
//    }


}