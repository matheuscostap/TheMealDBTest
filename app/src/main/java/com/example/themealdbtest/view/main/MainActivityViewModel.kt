package com.example.themealdbtest.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themealdbtest.model.AbstractModel
import com.example.themealdbtest.model.MealModel
import com.example.themealdbtest.model.RandomMealModel
import com.example.themealdbtest.repository.MealDBRepository
import com.example.themealdbtest.repository.room.TheMealDBDataBase

class MainActivityViewModel(private val repository: MealDBRepository, private val database: TheMealDBDataBase): ViewModel(){

    val event = MutableLiveData<AbstractModel<RandomMealModel>>()
    val favoritoEvent = MutableLiveData<AbstractModel<List<MealModel>>>()

    fun retornarReceitaAleatoria(){
        repository.retornarReceitaAleatoria {
            event.value = it.value
        }
    }

    fun retornarFavoritos(){
        favoritoEvent.value = AbstractModel(isLoading = true)
        database.mealDao().retornarFavoritos()
    }
}