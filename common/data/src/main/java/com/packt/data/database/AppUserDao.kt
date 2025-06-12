package com.packt.data.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: AppUser)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: String): AppUser?
}
