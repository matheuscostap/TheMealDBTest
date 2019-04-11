package com.example.themealdbtest

import android.app.Application
import androidx.room.Room
import com.example.themealdbtest.di.app_module
import com.example.themealdbtest.di.retrofit_client_module
import com.example.themealdbtest.repository.room.TheMealDBDataBase
import org.koin.android.ext.android.startKoin

class TheMealDBApplication: Application(){

    companion object {
        var database: TheMealDBDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()

        //Koin
        startKoin(this, listOf(app_module,retrofit_client_module))

        //Room
        database = Room.databaseBuilder(this, TheMealDBDataBase::class.java, "themealdb").allowMainThreadQueries().build()


    }
}