package com.example.motogymkhana.screens.stagedetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.Const
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentStageDetailsBinding
import com.example.motogymkhana.model.PostTimeRequestBody
import com.example.motogymkhana.model.Type
import com.example.motogymkhana.utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StageDetailsFragment : Fragment(R.layout.fragment_stage_details) {

    private val binding by viewBinding(FragmentStageDetailsBinding::class.java)

    private val viewModel by viewModels<StageDetailsViewModel>()

    private lateinit var adapter: UserAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter(object : UserListener {
            override fun openTimeMenu(participantID: Long) {
                viewModel.openTimeMenu(participantID)
            }

            override fun saveTime(requestBody: PostTimeRequestBody) {
                viewModel.postTime(requestBody)
            }

            override fun getCurrentTime(): String {
                return binding.currentTimeTextView.text.toString()
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val stageId = requireArguments().getLong(Const.STAGE_ID_KEY)

        viewModel.getStageInfo(id = stageId.toString(), type = Type.Offline.value)

        observeStagesState()

//        binding.currentTimeTextView.setOnClickListener {
//            viewModel.postTime(PostTimeRequestBody(time = binding.currentTimeTextView.text.toString()))
//        }
    }

    private fun observeStagesState() = with(binding) {
        collectFlow(viewModel.uiState) { state ->

            state.stageInfo?.let {
                stageTextView.text = it.title
                adapter.items = it.results
            }

            currentTimeTextView.text = state.currentTime
            progressBar.isVisible = state.isLoading

            state.userMessage.get()?.let {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }

}