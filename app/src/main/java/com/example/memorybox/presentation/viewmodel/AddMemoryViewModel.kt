package com.example.memorybox.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.repositories.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMemoryViewModel (private val repository: Repository) : ViewModel()  {

    private var imgLinkLiveData: MutableLiveData<String> = repository.getImgLinkLiveData()

    fun addMemory(memory: Memory){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMemory(memory)
        }
    }

    fun uploadImg(imgUri: Uri){
        repository.uploadImg(imgUri)
    }

    fun getImgLinkLiveData() : MutableLiveData<String>{
        return imgLinkLiveData
    }


    fun getUser(): FirebaseUser?{
        return repository.getUser()
    }
}