package com.example.mhst.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mhst.database.Therapist
import com.example.mhst.databinding.ItemTherapistBinding

class TherapistAdapter : ListAdapter<Therapist, TherapistAdapter.TherapistViewHolder>(TherapistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TherapistViewHolder {
        val binding = ItemTherapistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TherapistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TherapistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TherapistViewHolder(
        private val binding: ItemTherapistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(therapist: Therapist) {
            binding.apply {
                tvTherapistName.text = therapist.name
                tvSpecialization.text = therapist.specialization
                tvLocation.text = therapist.location
                tvAvailability.text = therapist.availability

                // Call button
                btnCall.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${therapist.phone}")
                    }
                    it.context.startActivity(intent)
                }

                // Email button
                btnEmail.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:${therapist.email}")
                        putExtra(Intent.EXTRA_SUBJECT, "Therapy Appointment Request")
                    }
                    it.context.startActivity(intent)
                }
            }
        }
    }

    class TherapistDiffCallback : DiffUtil.ItemCallback<Therapist>() {
        override fun areItemsTheSame(oldItem: Therapist, newItem: Therapist): Boolean {
            return oldItem.therapistId == newItem.therapistId
        }

        override fun areContentsTheSame(oldItem: Therapist, newItem: Therapist): Boolean {
            return oldItem == newItem
        }
    }
}
