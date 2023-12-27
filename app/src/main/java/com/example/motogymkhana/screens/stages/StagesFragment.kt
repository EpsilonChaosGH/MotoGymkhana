package com.example.motogymkhana.screens.stages

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentStagesBinding
import com.example.motogymkhana.model.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StagesFragment : Fragment(R.layout.fragment_stages) {

    private val binding by viewBinding(FragmentStagesBinding::class.java)

    private val viewModel by viewModels<StagesViewModel>()

    private val adapter = StageAdapter(object : StageListener{})


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        stagesRecyclerView.adapter = adapter
        stagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        observeStagesState()
    }

    private fun observeStagesState() = with(binding) {
        collectFlow(viewModel.uiState) { state ->

            adapter.items = state.stages
            progressBar.isVisible = state.isLoading

            state.userMessage.get()?.let {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }
}