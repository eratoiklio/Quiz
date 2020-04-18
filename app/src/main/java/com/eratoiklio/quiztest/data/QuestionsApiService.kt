package com.eratoiklio.quiztest.data

import com.eratoiklio.quiztest.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface QuestionsApiService {
    @GET("api.php")
    fun getProperties(@QueryMap map: HashMap<String, String>): Call<ApiResponse>
}