package com.post315.postest4_315.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resident")
data class Resident(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nik: String,
    val name: String,
    val gender: String,
    val maritalStatus: String,
    val rt: String,
    val rw: String,
    val desa: String,
    val kecamatan: String,
    val kabupaten: String
)
