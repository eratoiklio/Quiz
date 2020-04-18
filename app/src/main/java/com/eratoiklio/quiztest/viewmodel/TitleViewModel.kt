package com.eratoiklio.quiztest.viewmodel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.eratoiklio.quiztest.model.Categories
import com.eratoiklio.quiztest.data.QuestionClient
import com.eratoiklio.quiztest.model.ApiResponse
import com.eratoiklio.quiztest.view.TitleFragmentDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class TitleViewModel : ViewModel(), AdapterView.OnItemSelectedListener {
        private var categoryId: Int by Delegates.notNull<Int>()
        fun onClick(view: View){
        val map: HashMap<String, String> = HashMap()
        map["amount"] = "10"
        map["category"] = categoryId.toString()
        map["encode"] = "base64"
        QuestionClient.instance.getProperties(map).enqueue(object: Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("TEST", t.message)
            }
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToQuestionsFragemnt(response.body()!!))
            }
        } )
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i("TitleFragment","to do - block button")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val cat = parent?.getItemAtPosition(position) as Categories
        categoryId= cat.id
    }
}

@BindingAdapter("onItemSelected")
fun Spinner.setItemSelectedListener(itemSelectedListener: AdapterView.OnItemSelectedListener?) {
    onItemSelectedListener = itemSelectedListener
}
