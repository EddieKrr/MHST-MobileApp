package com.example.mhst.repository

import androidx.lifecycle.LiveData
import com.example.mhst.database.Therapist
import com.example.mhst.database.TherapistDao

class TherapistRepository(private val dao: TherapistDao) {

    val allTherapists: LiveData<List<Therapist>> = dao.getAllTherapists()

    suspend fun getTherapistById(id: Int): Therapist? {
        return dao.getTherapistById(id)
    }

    suspend fun insertTherapist(therapist: Therapist) {
        dao.insertTherapist(therapist)
    }

    suspend fun updateTherapist(therapist: Therapist) {
        dao.updateTherapist(therapist)
    }

    suspend fun deleteTherapist(therapist: Therapist) {
        dao.deleteTherapist(therapist)
    }

    suspend fun getTherapistCount(): Int {
        return dao.getTherapistCount()
    }
}