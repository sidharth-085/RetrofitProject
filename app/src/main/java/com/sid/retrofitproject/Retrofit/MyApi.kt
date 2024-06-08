package com.sid.retrofitproject.Retrofit

import com.sid.retrofitproject.DataClasses.Comments
import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
    @GET("comments")
    fun getComments(): Call<List<Comments>>
}