package com.thyagoneves.roommvvm.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.thyagoneves.roommvvm.database.models.User

@Dao
interface UserDao {

    /*As funções são do tipo 'suspend' para que possam
     ser chamadas dentro do escopo de uma corrotina.*/

    @Insert
    suspend fun insertUser(user : User)

    @Delete
    suspend fun deleteUser(user : User)

    @Query("SELECT COUNT(uid) FROM user")
    suspend fun getTotalUsers() : Long

    @Query("SELECT * FROM user")
    suspend fun usersList() : MutableList<User>

}