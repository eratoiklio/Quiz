package com.eratoiklio.quiztest

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponse(val results: List<Question>) : Serializable

data class Question(val question: String, @SerializedName ("correct_answer") val answer: String) : Serializable