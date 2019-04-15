package com.example.themealdbtest.view.main


import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.themealdbtest.R
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import android.view.WindowManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themealdbtest.TheMealDBApplication
import com.example.themealdbtest.model.AbstractModel
import com.example.themealdbtest.model.RandomMealModel
import com.example.themealdbtest.view.mealdetail.MealDetailActivity
import kotlinx.android.synthetic.main.content_meal_detail.*
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

    override fun onResume() {
        super.onResume()
        iniciarListaFavoritos()
    }

    private fun observarEventos(){
        viewModel.event.observe(this, Observer<AbstractModel<RandomMealModel>> { event->
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

    private fun iniciarListaFavoritos(){
        val favoritos = TheMealDBApplication.database?.mealDao()?.retornarFavoritos()

        favoritos?.let {
            val adapter = FavoriteListAdapter(this,it){mealModel, view ->
                startActivity(intentFor<MealDetailActivity>("mealModel" to mealModel),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,view,ViewCompat.getTransitionName(view) ?: "").toBundle())
            }
            rv_main_favorites.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            rv_main_favorites.adapter = adapter
        }
    }
}
