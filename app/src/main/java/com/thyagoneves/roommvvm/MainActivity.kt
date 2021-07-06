package com.thyagoneves.roommvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private var listUser: List<User> = mutableListOf()

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.database = AppDatabase.getInstance(this)
        this.userDao = this.database.userDao()

        supportActionBar?.hide()
        setSupportActionBar(binding.toolbar)

        viewModel =
            ViewModelProvider(this, UserViewModelFactory(UsersRepository(this.userDao)))
                .get(UserViewModel::class.java)

        viewModel.usersList.observe(this, {
            adapter = UserAdapter(viewModel.usersList)
            binding.recyclerUsers.adapter = adapter
            binding.recyclerUsers.hasFixedSize()
        })

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                CoroutineScope(Dispatchers.Unconfined).launch {

                    delUser(viewModel.usersList.value!!.get(viewHolder.adapterPosition))

                    runOnUiThread {

                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                       // adapter = UserAdapter(viewModel.usersList)

                    }
                }


            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerUsers)

    }

    private suspend fun delUser(user: User) {
            viewModel.deleteUser(user)
    }

    override fun onStart() {
        super.onStart()
        this.binding.btnAddUser.setOnClickListener {
            openAddUserActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUsers()
    }

    fun openAddUserActivity() {
        startActivity(Intent(this, AddUserActivity::class.java))
    }
}