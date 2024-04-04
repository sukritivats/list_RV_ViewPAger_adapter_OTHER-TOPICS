package com.example.navigation.Fragment1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.navigation.R
import com.example.navigation.databinding.Fragment1Binding
import com.example.navigation.databinding.Fragment2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Fragment1 : Fragment() {

    private lateinit var binding: Fragment1Binding

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

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply {
            title="Recipes"
        }
        binding=Fragment1Binding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {

                val responseBody = response.body()
                val recipeList= responseBody?.recipes!!
                val myAdapter=MyRecipeAdapter(recipeList)
                binding.viewPager2.adapter=myAdapter
                binding.viewPager2.orientation= ViewPager2.ORIENTATION_HORIZONTAL
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("Fragment1","onFailure: "+t.message)
            }
        })
    }

}