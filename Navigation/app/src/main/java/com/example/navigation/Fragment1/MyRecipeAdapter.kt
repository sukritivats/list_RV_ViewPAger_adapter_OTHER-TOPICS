package com.example.navigation.Fragment1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.R
import com.squareup.picasso.Picasso

class MyRecipeAdapter(val recipeArrayList: List<Recipe>)
    :RecyclerView.Adapter<MyRecipeAdapter.MyViewHolder>()
{
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var hMenu:TextView = itemView.findViewById(R.id.tvTitle)
        var hImage:ImageView = itemView.findViewById(R.id.imageView)
        var hInstructions:TextView = itemView.findViewById(R.id.tvInstructions)
        var hIngredients:TextView =itemView.findViewById(R.id.tvIngredients)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.each_item_fg1,parent,false))
    }

    override fun getItemCount(): Int {
        return recipeArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=recipeArrayList[position]
        holder.hMenu.text=currentItem.name
//        holder.hImage.setImageResource()
        Picasso.get().load(currentItem.image).into(holder.hImage)
        holder.hInstructions.text= currentItem.instructions.joinToString("\n")
        holder.hIngredients.text= currentItem.ingredients.joinToString("\n")
    }

}