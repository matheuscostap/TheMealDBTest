package com.example.themealdbtest.repository

import com.example.themealdbtest.model.RandomMealModel
import retrofit2.Call
import retrofit2.http.GET

interface MealDBDataSource {
    @GET("random.php")
    fun retornarReceitaAleatoria(): Call<RandomMealModel>
}