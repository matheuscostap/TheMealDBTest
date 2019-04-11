package com.example.themealdbtest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themealdbtest.model.AbstractModel
import com.example.themealdbtest.model.RandomMealModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface MealDBRepository{
    fun retornarReceitaAleatoria(data: (LiveData<AbstractModel<RandomMealModel>>) -> Unit)
}

class MealDBRepositoryImpl(private val dataSource: MealDBDataSource): MealDBRepository{

    override fun retornarReceitaAleatoria(data: (LiveData<AbstractModel<RandomMealModel>>) -> Unit) {
        //Loading handler
        val res = MutableLiveData<AbstractModel<RandomMealModel>>()
        res.value = AbstractModel(isLoading = true)
        data(res)

        dataSource.retornarReceitaAleatoria().enqueue(object : Callback<RandomMealModel>{
            override fun onFailure(call: Call<RandomMealModel>, t: Throwable) {
                res.value = AbstractModel(erro = t)
                data(res)
            }

            override fun onResponse(call: Call<RandomMealModel>, response: Response<RandomMealModel>) {
                res.value = AbstractModel(isSuccess = true, obj = response.body())
                data(res)
            }
        })
    }

}