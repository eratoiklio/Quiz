package com.eratoiklio.quiztest.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.eratoiklio.quiztest.R
import com.eratoiklio.quiztest.model.ApiResponse
import com.eratoiklio.quiztest.model.Question
import com.eratoiklio.quiztest.model.decode64
import com.eratoiklio.quiztest.model.extend
import com.eratoiklio.quiztest.view.QuestionsFragmentDirections


class QuestionsViewModel : ViewModel(), TextToSpeech.OnInitListener, RecognitionListener {
    companion object {
        private const val QUESTION_UTTERANCE = "question"
        private const val ANSWER_UTTERANCE = "answer"
        private const val ERROR_UTTERANCE = "error"
    }

    private lateinit var tts: TextToSpeech
    private lateinit var recognizer: SpeechRecognizer
    private lateinit var allQuestions: ApiResponse
    private var correctAnswerCount = 0
    private var questionNr: Int = 0
    private lateinit var context: Context
    private lateinit var currentQuestion: Question
    private var isCorrectCounterFlag = false
    val questionText = MutableLiveData<String>()
    val answers = MutableLiveData<List<String>>()
    fun setUp(allQuestions: ApiResponse, context: Context) {
        this.allQuestions = allQuestions
        initTtsAndRecognition(context)
    }

    private fun initTtsAndRecognition(context: Context) {
        this.context = context
        recognizer = SpeechRecognizer.createSpeechRecognizer(context)
        recognizer.setRecognitionListener(this)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.eratoiklio.quiztest")

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
        tts = TextToSpeech(context, this)
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onDone(utteranceId: String?) {
                val activity = context as Activity
                when (utteranceId) {
                    QUESTION_UTTERANCE -> {
                        activity.runOnUiThread {
                            recognizer.startListening(intent)
                        }
                    }
                    ANSWER_UTTERANCE -> {
                        nextQuestion()
                    }
                    ERROR_UTTERANCE -> {
                        activity.runOnUiThread { askQuestion(currentQuestion) }
                    }
                }
            }

            override fun onError(utteranceId: String?) {
            }

            override fun onStart(utteranceId: String?) {}
        })

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            currentQuestion = allQuestions.results[questionNr].decode64().extend()
            askQuestion(currentQuestion)
        }
    }

    override fun onReadyForSpeech(params: Bundle?) {
        isCorrectCounterFlag = false
    }

    override fun onRmsChanged(rmsdB: Float) {}

    override fun onBufferReceived(buffer: ByteArray?) {}

    override fun onPartialResults(partialResults: Bundle?) {}

    override fun onEvent(eventType: Int, params: Bundle?) {}

    override fun onBeginningOfSpeech() {
    }

    override fun onEndOfSpeech() {
    }

    override fun onError(error: Int) {
        tts.speak("Sorry I didn't understand", TextToSpeech.QUEUE_FLUSH, null,
            ERROR_UTTERANCE
        )
    }

    override fun onResults(results: Bundle?) {
        if (isCorrectCounterFlag) {
            return
        }
        val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: return
        val isCorrect = data.any {
            currentQuestion.answer.trim().toLowerCase() == it.toLowerCase()
        }
        if (isCorrect) {
            correctAnswerCount++
            tts.speak("That is correct", TextToSpeech.QUEUE_FLUSH, null,
                ANSWER_UTTERANCE
            )
        } else {
            tts.speak(
                "The correct answer is ${currentQuestion.answer}",
                TextToSpeech.QUEUE_FLUSH,
                null,
                ANSWER_UTTERANCE
            )
        }
        isCorrectCounterFlag = !isCorrectCounterFlag
    }

    private fun askQuestion(question: Question) {
        questionText.value = question.question
        answers.value = question.options
        tts.speak(
            question.question + question.options,
            TextToSpeech.QUEUE_FLUSH,
            null,
            QUESTION_UTTERANCE
        )
    }

    private fun nextQuestion() {
        val activity = context as Activity
        questionNr++
        if (questionNr < allQuestions.results.size) {
            currentQuestion = allQuestions.results[questionNr].decode64().extend()
            activity.runOnUiThread { askQuestion(currentQuestion) }
        } else {
            val controller = activity.findNavController(R.id.myNavHostFragment)
            controller.navigate(
                QuestionsFragmentDirections.actionQuestionsFragemntToResultsFragment(
                    correctAnswerCount
                )
            )
        }
    }

    fun onPause() {
        tts.shutdown()
    }
}