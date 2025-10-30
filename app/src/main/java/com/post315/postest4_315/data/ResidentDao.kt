package com.post315.postest4_315.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResidentDao {
    @Insert
    suspend fun insert(resident: Resident)

    @Query("SELECT * FROM resident")
    suspend fun getAll(): List<Resident>

    @Query("DELETE FROM resident")
    suspend fun deleteAll()
}
