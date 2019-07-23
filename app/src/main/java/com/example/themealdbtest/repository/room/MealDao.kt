package com.example.themealdbtest.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themealdbtest.model.MealModel
import java.util.*

@Dao
interface MealDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE) fun salvarFavorito(mealModel: MealModel): Long

    @Query("SELECT * FROM meal") fun retornarFavoritos(): LiveData<List<MealModel>>
}