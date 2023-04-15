package com.example.memorybox.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddInfoViewModel (private val repository: Repository) : ViewModel()  {

    private var imgLinkLiveData: MutableLiveData<String> = repository.getImgLinkLiveData()
    private var imgResumeLinkLiveData: MutableLiveData<String> = repository.getImgResumeLinkLiveData()
    private var imgIdLinkLiveData: MutableLiveData<String> = repository.getImgIdLinkLiveData()




    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }
    fun addUserProfile(user: User){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repository.addUserProfile(user)
        }
    }

    fun uploadImg(imgUri: Uri){
        repository.uploadImg(imgUri)
    }

    fun getImgLinkLiveData() : MutableLiveData<String>{
        return imgLinkLiveData
    }

    fun uploadResumeImg(imgUri: Uri){
        repository.uploadResumeImg(imgUri)
    }

    fun getImgResumeLinkLiveData() : MutableLiveData<String>{
        return imgResumeLinkLiveData
    }

    fun uploadImgId(imgUri: Uri){
        repository.uploadIdImg(imgUri)
    }

    fun getImgIdLinkLiveData() : MutableLiveData<String>{
        return imgIdLinkLiveData
    }



    fun getUser(): FirebaseUser?{
        return repository.getUser()
    }
}