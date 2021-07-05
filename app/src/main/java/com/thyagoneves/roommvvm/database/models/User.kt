package com.thyagoneves.roommvvm.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "first_name") val name : String,
    @ColumnInfo(name = "last_name") val lastname : String
) {
    @PrimaryKey(autoGenerate = true)
    var uid : Int = 0
}
