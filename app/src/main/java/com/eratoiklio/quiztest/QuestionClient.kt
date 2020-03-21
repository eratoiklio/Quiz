package com.eratoiklio.quiztest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuestionClient {
    const val BASEURL = "https://opentdb.com/"
        val instance by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .build()
            retrofit.create(QuestionsApiService::class.java)
        }
}