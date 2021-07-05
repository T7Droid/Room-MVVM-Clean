package com.thyagoneves.roommvvm.viewmodel.main

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thyagoneves.roommvvm.database.models.User
import com.thyagoneves.roommvvm.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel constructor(private val repository: UsersRepository) : ViewModel() {

    private val _usersList = MutableLiveData<List<User>>()
    var usersList: LiveData<List<User>>
    get() = _usersList

    init {
        usersList = _usersList
    }

    fun addUser(name: String, lastname: String){
        CoroutineScope(Dispatchers.IO).launch{
            repository.addUser(name, lastname)
        }
    }
    fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            _usersList.postValue(repository.getAllUsers())
        }
    }
   suspend fun getTotalUsers() : Long {
      return repository.getTotalUsers()
   }

    suspend fun deleteUser(user: User){
            repository.deleteUser(user)
    }
}