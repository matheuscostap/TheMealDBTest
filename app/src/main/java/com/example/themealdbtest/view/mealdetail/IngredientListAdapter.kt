package com.example.themealdbtest.view.mealdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themealdbtest.R
import com.example.themealdbtest.model.IngredientModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_ingredient.view.*

class IngredientListAdapter(val ctx: Context, val ingredients: List<IngredientModel>): RecyclerView.Adapter<IngredientListAdapter.IngredientsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_list_ingredient,parent,false)
        return IngredientsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredients[position]

        Picasso.get().load(ingredient.image).into(holder.iv_ingredient_image)
        holder.tv_ingredient_name.text = ingredient.name.capitalize()
        holder.tv_ingredient_measure.text = ingredient.measure
    }

    inner class IngredientsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val iv_ingredient_image = itemView.iv_ingredient_image
        val tv_ingredient_name = itemView.tv_ingredient_name
        val tv_ingredient_measure = itemView.tv_ingredient_measure
    }
}
