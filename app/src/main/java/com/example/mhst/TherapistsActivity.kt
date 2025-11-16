package com.example.mhst

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mhst.adapter.TherapistAdapter
import com.example.mhst.databinding.ActivityTherapistsBinding

class TherapistsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTherapistsBinding
    private val therapistViewModel: TherapistViewModel by viewModels()
    private lateinit var therapistAdapter: TherapistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTherapistsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("TherapistsActivity", "onCreate called")

        setupToolbar()
        setupRecyclerView()
        observeTherapists()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        Log.d("TherapistsActivity", "Setting up RecyclerView")
        therapistAdapter = TherapistAdapter()

        binding.rvTherapists.apply {
            layoutManager = LinearLayoutManager(this@TherapistsActivity)
            adapter = therapistAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeTherapists() {
        Log.d("TherapistsActivity", "Starting to observe therapists")
        binding.progressBar.visibility = View.VISIBLE

        therapistViewModel.allTherapists.observe(this) { therapists ->
            Log.d("TherapistsActivity", "Therapists received: ${therapists?.size ?: 0}")
            binding.progressBar.visibility = View.GONE

            if (therapists != null && therapists.isNotEmpty()) {
                Log.d("TherapistsActivity", "Submitting ${therapists.size} therapists to adapter")
                therapists.forEach { therapist ->
                    Log.d("TherapistsActivity", "Therapist: ${therapist.name}")
                }
                therapistAdapter.submitList(therapists)
            } else {
                Log.e("TherapistsActivity", "No therapists found!")
            }
        }
    }
}