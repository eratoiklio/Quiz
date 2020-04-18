package com.eratoiklio.quiztest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eratoiklio.quiztest.viewmodel.QuestionsViewModel
import com.eratoiklio.quiztest.R
import com.eratoiklio.quiztest.databinding.QuestionsFragmentBinding
import com.eratoiklio.quiztest.model.ApiResponse

class QuestionsFragment : Fragment() {
    private lateinit var binding: QuestionsFragmentBinding
    private val adapter = ListAdapter()
    private lateinit var viewModel: QuestionsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.questions_fragment, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(QuestionsViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = QuestionsFragmentArgs.fromBundle(arguments!!)
        if (args.questions as? ApiResponse == null) {
            return
        }
        viewModel.setUp(args.questions, context!!)
        binding.options.adapter = adapter
        viewModel.answers.observe(viewLifecycleOwner, Observer {
            onNewAnswers(it)
        })
        binding.options.layoutManager = LinearLayoutManager(activity)
    }

    private fun onNewAnswers(answers: List<String>) {
        adapter.setOptions(answers)
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}

