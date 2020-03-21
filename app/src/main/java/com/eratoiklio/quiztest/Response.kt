package com.eratoiklio.quiztest

import com.google.gson.annotations.SerializedName

data class ApiResponse(val results: List<Question>)

data class Question(val question: String, @SerializedName ("correct_answer") val answer: String)