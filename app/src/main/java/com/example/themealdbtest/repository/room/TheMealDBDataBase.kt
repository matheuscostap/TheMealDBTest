package com.example.themealdbtest.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themealdbtest.model.MealModel

@Database(entities = arrayOf(MealModel::class), version = 1)
abstract class TheMealDBDataBase: RoomDatabase(){
    abstract fun mealDao(): MealDao
}