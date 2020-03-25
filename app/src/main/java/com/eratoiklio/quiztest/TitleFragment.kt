package com.eratoiklio.quiztest

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.eratoiklio.quiztest.databinding.TitleFragmentBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

private const val RECORD_AUDIO_PERMISSION = 1
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

    override fun onStart() {
        super.onStart()
        activity?.let {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    RECORD_AUDIO_PERMISSION)
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
        binding.playBtn.setOnClickListener { view: View ->

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

        binding.categorySpinner.onItemSelectedListener= this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i("TitleFragment","to do - block button")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val cat = parent?.getItemAtPosition(position) as Categories
        categoryId= cat.id
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

                    Snackbar.make(binding.root, R.string.app_name, Snackbar.LENGTH_INDEFINITE).setAction(R.string.app_name) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", activity?.packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }.show()
                       binding.playBtn.setEnabled(false)
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

}
