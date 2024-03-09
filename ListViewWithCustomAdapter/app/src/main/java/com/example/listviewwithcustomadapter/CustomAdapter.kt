package com.example.listviewwithcustomadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

// CustomAdapter.kt
class CustomAdapter(private val context: Context, private val dataList: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.each_item, parent, false)
        }

        val imageView: ImageView = view?.findViewById(R.id.imageView)!!
        val textView: TextView = view.findViewById(R.id.textView)

        textView.text = dataList[position]
        imageView.setImageResource(R.drawable.profile)

        return view
    }
}
