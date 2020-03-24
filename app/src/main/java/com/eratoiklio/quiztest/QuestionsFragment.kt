package com.eratoiklio.quiztest

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.eratoiklio.quiztest.databinding.QuestionsFragmentBinding

private const val QUESTION_UTTERANCE = "question"
private const val ANSWER_UTTERANCE = "answer"
class QuestionsFragment : Fragment(), TextToSpeech.OnInitListener, RecognitionListener {
    private lateinit var binding: QuestionsFragmentBinding
    private lateinit var tts: TextToSpeech
    private lateinit var recognizer: SpeechRecognizer
    private lateinit var allQuestions: ApiResponse
    private var correctAnswerCount = 0
    private var questionNr: Int =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.questions_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = QuestionsFragmentArgs.fromBundle(arguments!!)
        initTtsAndRecognition()
        if (args.questions as? ApiResponse == null) {
            return
        }
        allQuestions = args.questions as ApiResponse
    }

    private fun askQuestion(question: Question) {
     tts.speak(question.question, TextToSpeech.QUEUE_FLUSH, null, QUESTION_UTTERANCE)

        binding.currentQuestion.text = question.question
    }

    fun initTtsAndRecognition() {
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
                Log.e("StartListening", "StartListening")
                if (utteranceId == QUESTION_UTTERANCE) {
                    activity?.runOnUiThread {
                        recognizer.startListening(intent)
                    }
                } else if (utteranceId == ANSWER_UTTERANCE){
                    nextQuestions()
                }
            }

            override fun onError(utteranceId: String?) {
                Log.e("Test", "Test")
            }

            override fun onStart(utteranceId: String?) {}
        })

    }

    override fun onPause() {
        super.onPause()
        tts.shutdown()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val currentQuestion = allQuestions.results[questionNr]
            askQuestion(currentQuestion)
        }
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Toast.makeText(context, "Listening", Toast.LENGTH_SHORT).show()
    }

    override fun onRmsChanged(rmsdB: Float) {}

    override fun onBufferReceived(buffer: ByteArray?) {}

    override fun onPartialResults(partialResults: Bundle?) {}

    override fun onEvent(eventType: Int, params: Bundle?) {}

    override fun onBeginningOfSpeech() {
        Toast.makeText(context, "You speak!", Toast.LENGTH_SHORT).show()
    }

    override fun onEndOfSpeech() {
    }

    override fun onError(error: Int) {}

    override fun onResults(results: Bundle?) {
        val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: return
        var isCorrect = false
        isCorrect = data.contains(allQuestions.results.get(questionNr).answer)
        if(isCorrect){
            correctAnswerCount++
            tts.speak("That is correct answer", TextToSpeech.QUEUE_FLUSH, null, ANSWER_UTTERANCE)
        }
        else{
            tts.speak("the correct answer is ${allQuestions.results.get(questionNr).answer}", TextToSpeech.QUEUE_FLUSH, null, ANSWER_UTTERANCE)
        }
    }
    private fun nextQuestions()
    {
        questionNr++
        if(questionNr< allQuestions.results.size-1)
        {
            var currentQuestion = allQuestions.results.get(questionNr)
            askQuestion(currentQuestion)
        }
    }
}

