package com.eratoiklio.quiztest

import android.util.Base64
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponse(val results: List<Question>) : Serializable

data class Question(val question: String, @SerializedName ("correct_answer") val answer: String) : Serializable

fun Question.decode64(): Question {
    val decodedQuestion = String(Base64.decode(question, Base64.DEFAULT))
    val decodedAnswer = String(Base64.decode(answer, Base64.DEFAULT))
    return Question(decodedQuestion, decodedAnswer)
}