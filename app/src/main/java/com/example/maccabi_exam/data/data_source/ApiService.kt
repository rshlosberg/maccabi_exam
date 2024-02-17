package com.example.maccabi_exam.data.data_source

import com.example.maccabi_exam.data.model.MaccabiModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/products?limit=100")
    suspend fun fetchMaccabiModel() : Response<MaccabiModel>
}