package com.example.mhst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mhst.database.MHSTDatabase
import com.example.mhst.database.Therapist
import com.example.mhst.repository.TherapistRepository
import kotlinx.coroutines.launch

class TherapistViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TherapistRepository

    // LiveData for all therapists
    val allTherapists: LiveData<List<Therapist>>

    init {
        val therapistDao = MHSTDatabase.getDatabase(application).TherapistDao()
        repository = TherapistRepository(therapistDao)
        allTherapists = repository.allTherapists
    }

    fun insertTherapist(therapist: Therapist) {
        viewModelScope.launch {
            repository.insertTherapist(therapist)
        }
    }

    fun updateTherapist(therapist: Therapist) {
        viewModelScope.launch {
            repository.updateTherapist(therapist)
        }
    }

    fun deleteTherapist(therapist: Therapist) {
        viewModelScope.launch {
            repository.deleteTherapist(therapist)
        }
    }
}