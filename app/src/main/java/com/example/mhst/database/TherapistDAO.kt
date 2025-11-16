package com.example.mhst.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TherapistDao {

    @Query("SELECT * FROM therapists ORDER BY name ASC")
    fun getAllTherapists(): LiveData<List<Therapist>>

    @Query("SELECT * FROM therapists WHERE therapistId = :id")
    suspend fun getTherapistById(id: Int): Therapist?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTherapist(therapist: Therapist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(therapists: List<Therapist>)

    @Update
    suspend fun updateTherapist(therapist: Therapist)

    @Delete
    suspend fun deleteTherapist(therapist: Therapist)

    @Query("DELETE FROM therapists")
    suspend fun deleteAllTherapists()

    @Query("SELECT COUNT(*) FROM therapists")
    suspend fun getTherapistCount(): Int
}


