package com.example.motogymkhana.screens.stage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentStageBinding
import com.example.motogymkhana.model.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StageFragment : Fragment(R.layout.fragment_stage) {

    private val binding by viewBinding(FragmentStageBinding::class.java)

    private val viewModel by viewModels<StageViewModel>()

    private val adapter = StageAdapter(object : StageListener{})


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