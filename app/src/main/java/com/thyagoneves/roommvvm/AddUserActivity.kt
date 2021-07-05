package com.thyagoneves.roommvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.thyagoneves.roommvvm.database.AppDatabase
import com.thyagoneves.roommvvm.database.daos.UserDao
import com.thyagoneves.roommvvm.databinding.ActivityAddUserBinding
import com.thyagoneves.roommvvm.repository.UsersRepository
import com.thyagoneves.roommvvm.viewmodel.main.UserViewModel
import com.thyagoneves.roommvvm.viewmodel.main.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUserActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var binding: ActivityAddUserBinding
    private lateinit var repository: UsersRepository
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        this.database = AppDatabase.getInstance(this)
        this.userDao = this.database.userDao()

        viewModel = ViewModelProvider(this, UserViewModelFactory(UsersRepository(this.userDao))).get(
            UserViewModel::class.java)

    }
    override fun onStart() {
        super.onStart()

        binding.btn.setOnClickListener {

            //Funções que interagem com o BD devem ser executadas dentro do escopo de uma corrotina
            CoroutineScope(Dispatchers.IO).launch {
                val result = saveUser(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString()
                )

                withContext(Dispatchers.Main){
                    if (result){
                        Toast.makeText(this@AddUserActivity,"Usuário salvo com sucesso!" , Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else Toast.makeText(this@AddUserActivity,"Falha ao salvar usuário", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private suspend fun saveUser(firstName: String, lastname: String): Boolean {

        if (firstName.isBlank() || firstName.isEmpty())
            return false

        if (lastname.isBlank() || lastname.isEmpty())
            return false

        viewModel.addUser(firstName, lastname)

        return true
    }
}