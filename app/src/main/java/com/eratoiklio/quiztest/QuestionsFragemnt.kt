package com.eratoiklio.quiztest

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
private const val BASE_URL = "https://opendb.com/api.php"
class QuestionsFragemnt : Fragment() {
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.questions_fragemnt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref: SharedPreferences = context!!.getSharedPreferences("ChosenCategory", 0)
        val i = pref.getInt("categoryId",0)
        val map: HashMap<String, String> = HashMap()
        map["amount"] = "10"
        map["category"] = i.toString()
        QuestionClient.instance.getProperties(map).enqueue(object: Callback<ApiResponse>{
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("TEST", t.message)
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                Log.e("TEST", response.body()?.toString() ?: "")
            }

        } )//)
//        Log.i("QuestionsFragment",i.toString())
    }
}
