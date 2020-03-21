package com.eratoiklio.quiztest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface QuestionsApiService {
    @GET("api.php")
    fun getProperties(@QueryMap map: HashMap<String, String>): Call<ApiResponse>
}