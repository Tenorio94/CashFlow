package com.familyapps.cashflow.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserRepository {

    @Insert
    fun insertUser(user: User): Long

    @Update
    fun updateUser(user: User)

    @Query("DELETE FROM USER")
    fun deleteAllUsers(): Int

    @Query("SELECT * FROM USER")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM USER WHERE EMAIL = :email")
    fun findUserByEmail(email: String): User

    @Query("SELECT * FROM USER WHERE EMAIL = :email")
    fun userExistsByEmail(email: String): Boolean
}