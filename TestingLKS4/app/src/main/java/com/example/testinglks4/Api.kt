package com.example.testinglks4

import com.example.testinglks4.Model.ModelUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @POST("tambah.php")
    fun registerUser(@Body user: ModelUser): Call<ResponseBody>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
        @Field("Username") Username: String,
        @Field("Password") Password: String
    ): Call<ResponseBody>

    @GET("character")
    fun getMort(): Call<ResponseMorty>
}