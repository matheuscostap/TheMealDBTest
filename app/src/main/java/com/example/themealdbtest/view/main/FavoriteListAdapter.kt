package com.example.themealdbtest.view.main

import android.content.Context
import android.graphics.BitmapShader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.themealdbtest.R
import com.example.themealdbtest.model.MealModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_favorite.view.*

class FavoriteListAdapter(val ctx: Context, val favorites: List<MealModel>,val onItemClick: (mealModel: MealModel, view: ImageView) -> Unit): RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_list_favorite,parent,false)
        return FavoriteListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        val mealModel = favorites[position]

        holder.iv_favorite_image.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                holder.iv_favorite_image.viewTreeObserver.removeOnPreDrawListener(this)
                val height = holder.iv_favorite_image.measuredHeight
                val width = holder.iv_favorite_image.measuredWidth

                Picasso.get().load(mealModel.strMealThumb).centerCrop().resize(width,height).into(holder.iv_favorite_image)
                return true
            }
        })

        holder.tv_favorite_name.text = mealModel.strMeal
        holder.item.setOnClickListener { onItemClick(mealModel,holder.iv_favorite_image) }

    }

    inner class FavoriteListViewHolder(val item: View): RecyclerView.ViewHolder(item){
        val iv_favorite_image = item.iv_favorite_image
        val tv_favorite_name = item.tv_favorite_name
    }
}