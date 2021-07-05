package com.thyagoneves.roommvvm.repository

import com.thyagoneves.roommvvm.database.daos.UserDao
import com.thyagoneves.roommvvm.database.models.User

class UsersRepository constructor(private val userDao: UserDao) {

   suspend fun getAllUsers() = userDao.usersList()

   suspend fun addUser(name: String, lastName: String){
      userDao.insertUser(User(name, lastName))
   }

   suspend fun getTotalUsers() : Long = userDao.getTotalUsers()

   suspend fun deleteUser(user: User){
      userDao.deleteUser(user)
   }

}