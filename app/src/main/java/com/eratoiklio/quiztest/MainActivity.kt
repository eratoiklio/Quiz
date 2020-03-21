package com.eratoiklio.quiztest

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(){
    //, TextToSpeech.OnInitListener, RecognitionListener {

//    private lateinit var tts: TextToSpeech
//    private lateinit var recognizer: SpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
//        tts = TextToSpeech(this, this)
//        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
//            override fun onDone(utteranceId: String?) {
//                if (utteranceId == "test") {
//                    runOnUiThread {
//                        recognizer.startListening(intent)
                //    }
                }
            }
//
//            override fun onError(utteranceId: String?) {}
//
//            override fun onStart(utteranceId: String?) {}
   //     })
//        recognizer = SpeechRecognizer.createSpeechRecognizer(this)
//        recognizer.setRecognitionListener(this)
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.eratoiklio.quiztest")
//
//        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
    //}

//    override fun onPause() {
//        super.onPause()
//        tts.shutdown()
//    }
//
//    override fun onInit(status: Int) {
//        if (status == TextToSpeech.SUCCESS) {
//            tts.speak("Hello world!", TextToSpeech.QUEUE_ADD, null, "test")
//        }
//    }
//
//    override fun onReadyForSpeech(params: Bundle?) {
//        Toast.makeText(this, "Listening", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onRmsChanged(rmsdB: Float) {}
//
//    override fun onBufferReceived(buffer: ByteArray?) {}
//
//    override fun onPartialResults(partialResults: Bundle?) {}
//
//    override fun onEvent(eventType: Int, params: Bundle?) {}
//
//    override fun onBeginningOfSpeech() {
//        Toast.makeText(this, "You speak!", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onEndOfSpeech() {
//        Toast.makeText(this, "Why you stop?", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onError(error: Int) {}
//
//    override fun onResults(results: Bundle?) {
//        val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: return
//        for (text in data) {
//            Log.e("Test", text)
//        }
//    }
//}
