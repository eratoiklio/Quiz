package com.eratoiklio.quiztest

import android.util.Base64
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlin.random.Random

data class ApiResponse(val results: List<Question>) : Serializable

data class Question(val question: String, @SerializedName ("correct_answer") val answer: String, @SerializedName("incorrect_answers") val options: ArrayList<String>) : Serializable

fun Question.decode64(): Question {
    val decodedQuestion = String(Base64.decode(question, Base64.DEFAULT))
    val decodedAnswer = String(Base64.decode(answer, Base64.DEFAULT))
    val decodedOptions = ArrayList<String>()
    for (option in options) {
        decodedOptions.add(String(Base64.decode(option, Base64.DEFAULT)))
    }
    return Question(decodedQuestion, decodedAnswer, decodedOptions)
}
 fun Question.extend(): Question {
     val optionsCount: Int = options.size
     val position = Random.nextInt(0, optionsCount)
     options.add(position, answer)
     return Question(question, answer, options)
 }