package com.thyagoneves.roommvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.thyagoneves.roommvvm.database.models.User
import com.thyagoneves.roommvvm.databinding.ItemUserBinding

class UserAdapter(
    private val onItemCliked: (User) -> Unit
    ) : RecyclerView.Adapter<UserAdapter.MainViewHolder>() {

    private lateinit var users: LiveData<List<User>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, posicao: Int) {
        val user = users.value!!.get(posicao)
        holder.bind(user, onItemCliked)
    }

    override fun getItemCount(): Int {
       return users.value!!.size
    }

    fun loadUsers(userList: LiveData<List<User>>){
        users = userList
    }

     inner class MainViewHolder constructor(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user: User, onItemCliked: (User) -> Unit){

            val nome = "${user.name } ${user.lastname}"
            binding.txtName.text = nome

            binding.root.setOnClickListener {
                onItemCliked(user)
            }
        }
    }
}
