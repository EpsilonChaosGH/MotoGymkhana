package com.example.motogymkhana.screens.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.Const
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentMenuBinding
import com.example.motogymkhana.databinding.FragmentTestBinding
import com.example.motogymkhana.utils.collectFlow
import com.example.motogymkhana.screens.stages.StageAdapter
import com.example.motogymkhana.screens.stages.StageListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.random.Random

@AndroidEntryPoint
class FragmentTest : Fragment(R.layout.fragment_test) {

    private val binding by viewBinding(FragmentTestBinding::class.java)
    private lateinit var request: Request
    private val client = OkHttpClient()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        binding.ipEditTextText.setText(Const.controllerIp)

//        min1 min2  sek2  sek1  mils1 mils2

        postRequestButton.setOnClickListener {
            if (binding.postRequestButton.text.isNotEmpty()) {
                post(binding.postRequestButton.text.toString())
            }
        }

    }

    private fun post(post: String) {
        lifecycleScope.launch(Dispatchers.IO) {

            request = Request.Builder().url("http://${binding.ipEditTextText.text}/$post").build()

            try {
                var response = client.newCall(request).execute()
                if (response.isSuccessful) {
                   requireActivity().runOnUiThread{
                       binding.responseTextView.text = response.body()?.string()
                   }
                }
            } catch (e: IOException) {
                requireActivity().runOnUiThread {
                    binding.responseTextView.text = e.message
                }
            }
        }
    }
}

