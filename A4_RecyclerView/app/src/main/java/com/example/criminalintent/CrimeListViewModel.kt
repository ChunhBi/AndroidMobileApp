package com.example.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeListViewModel"
class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()

    private val _crimes: MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())
    val crimes : StateFlow<List<Crime>>
        get() = _crimes.asStateFlow()
    init {
        viewModelScope.launch {
            crimeRepository.getCrimes().collect{
                _crimes.value = it
            }
        }
    }
//    private val _crimes: MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())
//    val crimes : StateFlow<List<Crime>>
//            get() = _crimes.asStateFlow()
//
//    init {
//        for (i in 0 until 100) {
//            val crime = Crime(
//                id = UUID.randomUUID(),
//                title ="Crime #$i",
//                date = Date(),
//                isSolved = i % 2 == 0,
//                requiresPolice = i % 2 == 1,
//            )
//
//            _crimes.value += crime
//        }
//    }
}