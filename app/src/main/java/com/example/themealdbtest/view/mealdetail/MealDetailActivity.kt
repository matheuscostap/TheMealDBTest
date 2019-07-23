package com.example.themealdbtest.view.mealdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themealdbtest.R
import com.example.themealdbtest.TheMealDBApplication
import com.example.themealdbtest.model.IngredientModel
import com.example.themealdbtest.model.MealModel
import com.example.themealdbtest.repository.room.TheMealDBDataBase
import com.example.themealdbtest.repository.room.TheMealDBDataBase_Impl
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_meal_detail.*
import org.koin.android.ext.android.inject
import java.lang.Exception
import kotlin.reflect.full.memberProperties

class MealDetailActivity : AppCompatActivity() {

    private val viewModel: MealDetailViewModel by inject()
    lateinit var mealModel: MealModel
    private val ingredientes = mutableListOf<IngredientModel>()
    private val ingredientImageBase = "https://www.themealdb.com/images/ingredients/*-Small.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_detail)

        val bundle = intent.extras
        bundle?.let {
            mealModel = it.getSerializable("mealModel") as MealModel
        }

        supportPostponeEnterTransition()

        var height = 0
        var width = 0

        iv_meal.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                iv_meal.viewTreeObserver.removeOnPreDrawListener(this)
                height = iv_meal.measuredHeight
                width = iv_meal.measuredWidth

                Picasso.get().load(mealModel.strMealThumb).centerCrop().resize(width,height).into(iv_meal,object : Callback{
                    override fun onSuccess() {
                        supportStartPostponedEnterTransition()
                    }
                    override fun onError(e: Exception?) {
                        supportStartPostponedEnterTransition()
                    }
                })
                return true
            }
        })

        tv_detail_meal.text = mealModel.strMeal
        tv_detail_category.text = "- ${mealModel.strCategory} -"
        tv_detail_area.text = "- ${mealModel.strArea} -"
        tv_detail_method.text = mealModel.strInstructions
        rv_detail_ingredients.isNestedScrollingEnabled = false

        btn_detail_youtube.setOnClickListener {
            abrirYoutube()
        }

        btn_detail_favorite.setOnClickListener {
            observarVM()
            salvarFavorito()
        }

        iniciarListaIngredientes()
        iniciarRecyclerView()
    }

    private fun iniciarListaIngredientes(){
        val listaIngredientes = mutableListOf<String>()
        val listaQuantidades = mutableListOf<String>()

        for (atributo in MealModel::class.memberProperties){
            if (atributo.name.contains("Ingredient")){
                val conteudo = atributo.get(mealModel)
                if (conteudo != null){
                    conteudo as String
                    if (conteudo.isNotEmpty()){
                        listaIngredientes.add(conteudo)
                    }
                }
            }else if (atributo.name.contains("Measure")){
                val conteudo = atributo.get(mealModel)
                if (conteudo != null){
                    conteudo as String
                    if (conteudo.isNotEmpty()){
                        listaQuantidades.add(conteudo)
                    }
                }
            }
        }

        for (i in 0 until listaIngredientes.size){
            val url = ingredientImageBase.replace("*",listaIngredientes[i])
            ingredientes.add(IngredientModel(listaIngredientes[i],listaQuantidades[i], url.replace(" ","%20")))
        }
    }

    private fun observarVM(){
        viewModel.event.observe(this, Observer {
            if (it.isSuccess){
                Snackbar.make(btn_detail_favorite, R.string.detail_receita_favorito, Snackbar.LENGTH_LONG).show()
            }else if (it.erro != null){
                Snackbar.make(btn_detail_favorite, R.string.detail_receita_favorito_erro, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun iniciarRecyclerView(){
        val adapter = IngredientListAdapter(this,ingredientes)
        rv_detail_ingredients.layoutManager = GridLayoutManager(this,3)
        rv_detail_ingredients.adapter = adapter
    }

    private fun abrirYoutube(){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mealModel.strYoutube)))
    }

    private fun salvarFavorito(){
        viewModel.salvarFavorito(mealModel)
    }

}
