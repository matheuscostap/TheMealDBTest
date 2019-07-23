package com.example.themealdbtest.view.mealdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themealdbtest.model.AbstractModel
import com.example.themealdbtest.model.MealModel
import com.example.themealdbtest.repository.room.TheMealDBDataBase


class MealDetailViewModel(private val database: TheMealDBDataBase): ViewModel(){

    val event = MutableLiveData<AbstractModel<Long>>()

    fun salvarFavorito(mealModel: MealModel){
        event.value = AbstractModel(isLoading = true)

        if (database.mealDao().salvarFavorito(mealModel) > 0){
            event.value = AbstractModel(isSuccess = true)
        }else{
            event.value = AbstractModel(erro = Throwable())
        }
    }
}