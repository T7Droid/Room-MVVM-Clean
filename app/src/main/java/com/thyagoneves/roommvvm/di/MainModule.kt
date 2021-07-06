package com.thyagoneves.roommvvm.di

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