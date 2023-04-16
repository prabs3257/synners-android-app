package com.example.memorybox.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Competition
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private var userLiveData:MutableLiveData<FirebaseUser> = repository.getUserLiveData()
    private var userMongoLiveData:MutableLiveData<User> = repository.getUserMongoLiveData()

    private var competitionLiveData:MutableLiveData<List<Competition>> = repository.getCompetitionsLiveData()

    fun getCompetitions(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCompetitions()
        }
    }

    fun getCompetitionLiveData() : MutableLiveData<List<Competition>>{
        return competitionLiveData
    }

    fun googleLogin(account: GoogleSignInAccount?){
        repository.googleLogin(account)
    }
    fun logout(){
        repository.logout()
    }

    fun getUserById(id:String){
        Log.d("google login", "sdeweehihiihihi")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserById(id)
        }
    }

    fun getUserMongoLiveData() : MutableLiveData<User>{
        return userMongoLiveData
    }


    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repository.addUser(user)
        }
    }

    fun getUserLiveData() : MutableLiveData<FirebaseUser>{
        return userLiveData
    }
}