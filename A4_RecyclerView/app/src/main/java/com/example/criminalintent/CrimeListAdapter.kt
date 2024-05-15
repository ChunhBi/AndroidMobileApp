package com.example.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.ListItemCrimeBinding
import com.example.criminalintent.databinding.ListItemSeriousCrimeBinding
import java.util.UUID




class CrimeHolder (
    val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(crime: Crime, onCrimeClicked: (crimeId: UUID) -> Unit) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
        binding.root.setOnClickListener {
            onCrimeClicked(crime.id)
        }
        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class SeriousCrimeHolder (
    val binding: ListItemSeriousCrimeBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(crime: Crime, onCrimeClicked: (crimeId: UUID) -> Unit) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
        binding.root.setOnClickListener {
            onCrimeClicked(crime.id)
        }
        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}


class CrimeListAdapter (
    private val crimes: List<Crime>,
    private val onCrimeClicked: (crimeId: UUID) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when(viewType) {
            1 ->  {
                val binding = ListItemSeriousCrimeBinding.inflate(inflater, parent, false)
                return SeriousCrimeHolder(binding)
            }
            else -> {
                val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
                return CrimeHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        when (holder) {
            is CrimeHolder -> {
                holder.bind(crime, onCrimeClicked)
            }
            is SeriousCrimeHolder -> {
                holder.bind(crime, onCrimeClicked)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val crime = crimes[position]
        return if (crime.requiresPolice) 1 else 0
    }

    override fun getItemCount() = crimes.size
}