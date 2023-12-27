package com.example.motogymkhana.screens.stagedetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentStageDetailsBinding
import com.example.motogymkhana.model.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StageDetailsFragment : Fragment(R.layout.fragment_stage_details) {

    private val binding by viewBinding(FragmentStageDetailsBinding::class.java)

    private val viewModel by viewModels<StageDetailsViewModel>()

    private val adapter = UserAdapter(object : UserListener{})


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel.getStateInfo()


        observeMessage()
        observeStageState()
    }

    private fun observeStageState() = with(binding) {
        collectFlow(viewModel.state) { state ->
            textView2.text = state.champTitle
            adapter.items = state.results
        }
    }

    private fun observeMessage() = with(binding) {
        collectFlow(viewModel.userMessage) { message ->
            message.get()?.let {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }
}