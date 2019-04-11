package com.example.themealdbtest.view.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import com.example.themealdbtest.R
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import android.view.WindowManager
import android.os.Build
import com.example.themealdbtest.view.mealdetail.MealDetailActivity
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity() {

    //Injection
    private val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.themealdbtest.R.layout.activity_main)

        /*val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)*/

        btn_random_recipe.setOnClickListener {
            viewModel.retornarReceitaAleatoria()
        }

        observarEventos()
    }

    private fun observarEventos(){
        viewModel.event.observe(this, Observer { event->
            if (event != null){
                if (event.isLoading){
                    progress_main.visibility = View.VISIBLE
                }else{
                    progress_main.visibility = View.GONE
                    if (event.isSuccess){
                        toast("Sucesso")
                        event.obj?.let {
                            startActivity(intentFor<MealDetailActivity>("mealModel" to it.meals[0]))
                        }
                        Log.i("MainActivity","Sucesso -> ${event.obj}")
                    }else{
                        toast("Erro")
                    }
                }
            }
        })
    }
}
