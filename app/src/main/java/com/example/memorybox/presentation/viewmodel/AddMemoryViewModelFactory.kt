package com.example.memorybox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memorybox.data.repositories.Repository

class AddMemoryViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddMemoryViewModel(repository) as T
    }


}