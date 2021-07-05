package com.thyagoneves.roommvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.thyagoneves.roommvvm.adapters.UserAdapter
import com.thyagoneves.roommvvm.database.AppDatabase
import com.thyagoneves.roommvvm.database.daos.UserDao
import com.thyagoneves.roommvvm.database.models.User
import com.thyagoneves.roommvvm.databinding.ActivityMainBinding
import com.thyagoneves.roommvvm.repository.UsersRepository
import com.thyagoneves.roommvvm.viewmodel.main.UserViewModel
import com.thyagoneves.roommvvm.viewmodel.main.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private var userList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.database = AppDatabase.getInstance(this)
        this.userDao = this.database.userDao()

        viewModel =
            ViewModelProvider(this, UserViewModelFactory(UsersRepository(this.userDao)))
                .get(UserViewModel::class.java)
        }

    private fun delUser(user: User) {
    CoroutineScope(Dispatchers.IO).launch {
        viewModel.deleteUser(user)
    }
    }

    override fun onStart() {
        super.onStart()
        this.binding.btnAddUser.setOnClickListener {
            openAddUserActivity()
        }

        adapter = UserAdapter {
                user -> delUser(user)
        }

        viewModel.usersList.observe(this, {
            binding.recyclerUsers.adapter = adapter

            binding.recyclerUsers.hasFixedSize()
            userList.clear()
            adapter.loadUsers(viewModel.usersList)

            supportActionBar?.setTitle("Usuários salvos: ${it.size}")
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUsers()
    }

    fun openAddUserActivity() {
        startActivity(Intent(this, AddUserActivity::class.java))
    }
}