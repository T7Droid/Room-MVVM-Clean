package com.thyagoneves.roommvvm.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thyagoneves.roommvvm.repository.UsersRepository

class UserViewModelFactory constructor(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            UserViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("View Model Not Found")
        }
    }


}