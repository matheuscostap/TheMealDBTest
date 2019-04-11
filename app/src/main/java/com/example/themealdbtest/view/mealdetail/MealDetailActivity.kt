package com.example.themealdbtest.view.mealdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.ViewTreeObserver
import com.example.themealdbtest.R
import com.example.themealdbtest.TheMealDBApplication
import com.example.themealdbtest.model.IngredientModel
import com.example.themealdbtest.model.MealModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_meal_detail.*
import kotlin.reflect.full.memberProperties

class MealDetailActivity : AppCompatActivity() {

    lateinit var mealModel: MealModel
    val ingredientes = mutableListOf<IngredientModel>()
    val ingredientImageBase = "https://www.themealdb.com/images/ingredients/*-Small.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_detail)

        val bundle = intent.extras
        bundle?.let {
            mealModel = it.getSerializable("mealModel") as MealModel
        }

        var height = 0
        var width = 0

        iv_meal.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                iv_meal.viewTreeObserver.removeOnPreDrawListener(this)
                height = iv_meal.measuredHeight
                width = iv_meal.measuredWidth

                Picasso.get().load(mealModel.strMealThumb).centerCrop().resize(width,height).into(iv_meal)
                return true
            }
        })

        tv_detail_meal.text = mealModel.strMeal
        tv_detail_category.text = "- ${mealModel.strCategory} -"
        tv_detail_area.text = "- ${mealModel.strArea} -"
        tv_detail_method.text = mealModel.strInstructions
        rv_detail_ingredients.isClickable = false

        btn_detail_youtube.setOnClickListener {
            abrirYoutube()
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

    private fun iniciarRecyclerView(){
        val adapter = IngredientListAdapter(this,ingredientes)
        rv_detail_ingredients.layoutManager = GridLayoutManager(this,3)
        rv_detail_ingredients.adapter = adapter
    }

    private fun abrirYoutube(){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mealModel.strYoutube)))
    }

}
