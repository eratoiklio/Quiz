package com.eratoiklio.quiztest.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eratoiklio.quiztest.R
import com.eratoiklio.quiztest.model.Categories
import com.eratoiklio.quiztest.databinding.TitleFragmentBinding
import com.eratoiklio.quiztest.viewmodel.TitleViewModel
import com.google.android.material.snackbar.Snackbar

private const val RECORD_AUDIO_PERMISSION = 1
class TitleFragment : Fragment() {
    private lateinit var binding: TitleFragmentBinding
    private lateinit var viewModel: TitleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.title_fragment, container, false)
        viewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        activity?.let {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    RECORD_AUDIO_PERMISSION
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter: ArrayAdapter<Categories> = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item, Categories.values()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RECORD_AUDIO_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                    binding.playBtn.setEnabled(true)
                } else {

                    Snackbar.make(binding.root,
                        R.string.app_name, Snackbar.LENGTH_INDEFINITE).setAction(
                        R.string.app_name
                    ) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", activity?.packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }.show()
                       binding.playBtn.setEnabled(false)
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

}
