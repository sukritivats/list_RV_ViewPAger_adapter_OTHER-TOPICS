package com.example.navigation.Fragment1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.navigation.R
import com.example.navigation.databinding.FragmentCuisinesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentCuisines : Fragment() {

    private lateinit var binding:FragmentCuisinesBinding

    private val retrofitBuilder= Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterfaces::class.java)

    private val data = retrofitBuilder.getData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCuisinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {

                val responseBody = response.body()
                val recipeList= responseBody?.recipes!!

                val data = StringBuilder()
                for(i in recipeList){
                    data.append(i.name).append("\n\n")
                }
                binding.tvDishName.text=data

            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("FragmentCuisines","onFailure: "+t.message)
            }
        })

    }

}