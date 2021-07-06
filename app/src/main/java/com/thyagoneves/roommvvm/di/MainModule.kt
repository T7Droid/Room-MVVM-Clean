package com.thyagoneves.roommvvm.di

import android.content.Context
import com.thyagoneves.roommvvm.database.AppDatabase
import com.thyagoneves.roommvvm.database.daos.UserDao
import com.thyagoneves.roommvvm.database.daos.UserDao_Impl
import com.thyagoneves.roommvvm.repository.UsersRepository
import com.thyagoneves.roommvvm.viewmodel.main.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    factory{
        UsersRepository(
            userDao = get()
        )

    }

    viewModel {
        UserViewModel(
            repository = get()
        )
    }
}