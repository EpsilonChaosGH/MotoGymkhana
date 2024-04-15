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
import com.example.motogymkhana.data.model.ChampionshipResponse
import com.example.motogymkhana.data.model.getResult
import com.example.motogymkhana.databinding.FragmentMenuBinding
import com.example.motogymkhana.databinding.FragmentTestBinding
import com.example.motogymkhana.model.Type
import com.example.motogymkhana.utils.collectFlow
import com.example.motogymkhana.screens.stages.StageAdapter
import com.example.motogymkhana.screens.stages.StageListener
import com.example.motogymkhana.utils.millisecondToMinutes
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import kotlin.random.Random



@AndroidEntryPoint
class FragmentTest : Fragment(R.layout.fragment_test) {

    private var autoRefresh = false

    private val binding by viewBinding(FragmentTestBinding::class.java)
    private lateinit var request: Request
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        binding.postRequestButton.isEnabled = false
        binding.postRequestButton.isVisible = false
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://192.168.4.1/")
//            .client(
//                OkHttpClient.Builder()
//                    .addInterceptor(HttpLoggingInterceptor().also {
//                        it.level = HttpLoggingInterceptor.Level.BODY
//                    })
//                    .build()
//            )
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()

//        val testService = retrofit.create(TestService::class.java)
//
//        binding.retrofitRequestButton.setOnClickListener {
//            viewLifecycleOwner.lifecycleScope.launch {
//                try {
//                    binding.retrofitTextView.text =
//                        testService.getTest().getResult().temperatureC.millisecondToMinutes()
//                } catch (e: Exception) {
//                    binding.retrofitTextView.text = e.message.toString()
//                }
//            }
//        }



        binding.ipEditTextText.setText(Const.controllerIp)

        postRequestButton.setOnClickListener {
            if (binding.postEditTextText.text.isNotEmpty()) {
                if (autoRefresh) {
                    autoRefresh()
                } else {
                    post(binding.postEditTextText.text.toString())
                }
            }
        }

        binding.switchBt.setOnCheckedChangeListener { buttonView, isChecked ->
            autoRefresh = isChecked
            if (isChecked) {
                autoRefresh()
            }
        }
    }

    private fun autoRefresh() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            while (autoRefresh) {
                post(binding.postEditTextText.text.toString())
                delay(1000)
            }
        }
    }

    private fun post(post: String) {
//        requireActivity().runOnUiThread {
//            binding.textView.text = Random.nextInt(10000).toString()
//        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                request = Request.Builder().url("http://${binding.ipEditTextText.text}/$post").build()
                val response = client.newCall(request).execute()
                val time = response.getResult().toDouble().millisecondToMinutes()
                requireActivity().runOnUiThread {
                    binding.responseTextView.text = time
                }

            } catch (e: IOException) {
                requireActivity().runOnUiThread {
                    binding.responseTextView.text = e.message
                }
            }
        }
    }
}