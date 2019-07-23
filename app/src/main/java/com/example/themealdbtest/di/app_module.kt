package com.example.themealdbtest.di

import androidx.room.Room
import com.example.themealdbtest.repository.MealDBRepository
import com.example.themealdbtest.repository.MealDBRepositoryImpl
import com.example.themealdbtest.repository.room.TheMealDBDataBase
import com.example.themealdbtest.view.main.MainActivityViewModel
import com.example.themealdbtest.view.mealdetail.MealDetailViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.applicationContext
import org.koin.dsl.module.module

val app_module = module {
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { MealDetailViewModel(get()) }
    single { MealDBRepositoryImpl(get()) as MealDBRepository }

    //Database room
    single { Room.databaseBuilder(get(), TheMealDBDataBase::class.java, "themealdb").allowMainThreadQueries().build() }
}