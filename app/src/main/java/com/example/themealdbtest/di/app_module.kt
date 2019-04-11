package com.example.themealdbtest.di

import com.example.themealdbtest.repository.MealDBRepository
import com.example.themealdbtest.repository.MealDBRepositoryImpl
import com.example.themealdbtest.view.main.MainActivityViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val app_module = module {
    viewModel { MainActivityViewModel(get()) }
    single { MealDBRepositoryImpl(get()) as MealDBRepository }
}