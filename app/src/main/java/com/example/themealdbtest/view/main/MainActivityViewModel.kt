package com.example.themealdbtest.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.themealdbtest.model.AbstractModel
import com.example.themealdbtest.model.RandomMealModel
import com.example.themealdbtest.repository.MealDBRepository

class MainActivityViewModel(private val repository: MealDBRepository): ViewModel(){

    val event = MutableLiveData<AbstractModel<RandomMealModel>>()

    fun retornarReceitaAleatoria(){
        repository.retornarReceitaAleatoria {
            event.value = it.value
        }
    }
}