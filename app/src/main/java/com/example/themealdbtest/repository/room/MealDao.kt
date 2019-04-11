package com.example.themealdbtest.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themealdbtest.model.MealModel

@Dao
interface MealDao{
    @Insert(onConflict = OnConflictStrategy.ABORT) fun salvarFavorito(mealModel: MealModel)

    @Query("SELECT * FROM meal") fun retornarFavoritos(): List<MealModel>
}