package com.example.navigation.Fragment1

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterfaces {

    @GET("recipes")
    fun getData():Call<MyData>
}