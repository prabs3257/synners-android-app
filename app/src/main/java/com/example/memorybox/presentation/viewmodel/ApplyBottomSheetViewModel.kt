package com.example.memorybox.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Competition
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplyBottomSheetViewModel(private val repository: Repository) : ViewModel() {

    private var userLiveData: MutableLiveData<FirebaseUser> = repository.getUserLiveData()
    private var userMongoLiveData: MutableLiveData<User> = repository.getUserMongoLiveData()

    private var competitionLiveData: MutableLiveData<List<Competition>> = repository.getCompetitionsLiveData()



    fun getUser(): FirebaseUser?{
        return repository.getUser()
    }

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }
    fun addRequest(teamId:String, name: String){
        Log.d("google login", "sdeweehihiihihi")
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repository.addRequest(teamId,name)
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