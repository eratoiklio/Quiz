package com.eratoiklio.quiztest

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.eratoiklio.quiztest.databinding.TitleFragmentBinding
import kotlin.properties.Delegates


/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment(), OnItemSelectedListener {
    private lateinit var binding: TitleFragmentBinding
    private lateinit var editor: SharedPreferences.Editor
    private var categoryId: Int by Delegates.notNull<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.title_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter: ArrayAdapter<Categories> = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item, Categories.values()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.categorySpinner.adapter = adapter

         val pref: SharedPreferences = context!!.getSharedPreferences("ChosenCategory", 0)

        editor = pref.edit()

        binding.playBtn.setOnClickListener { view: View ->
           editor.putInt("categoryId", categoryId).apply()
            view.findNavController().navigate(R.id.action_titleFragment_to_questionsFragemnt)
        }

        binding.categorySpinner.onItemSelectedListener= this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i("TitleFragment","to do - block button")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val cat = parent?.getItemAtPosition(position) as Categories
        categoryId= cat.id
    }


}
