package com.eratoiklio.quiztest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.eratoiklio.quiztest.databinding.ResultsFragmentBinding

class ResultsFragment : Fragment() {
    private lateinit var binding: ResultsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate<ResultsFragmentBinding>(inflater,
            R.layout.results_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = ResultsFragmentArgs.fromBundle(arguments!!).correctNum.toString()
        binding.result.text = "${args}/10"
        binding.againBtn.setOnClickListener{ view : View ->
            view.findNavController().navigate(ResultsFragmentDirections.actionResultsFragmentToTitleFragment())
        }
    }

    private fun getShareIntent() : Intent {
       val args = ResultsFragmentArgs.fromBundle(arguments!!).correctNum.toString()
       val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, "${args}/10")
        return shareIntent
    }
    private fun shareResult() {
        startActivity(getShareIntent())
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.share_menu, menu)
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // hide the menu item if it doesn't resolve
            menu?.findItem(R.id.shareIcon)?.setVisible(false)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.shareIcon -> shareResult()
        }
        return super.onOptionsItemSelected(item)
    }

}
