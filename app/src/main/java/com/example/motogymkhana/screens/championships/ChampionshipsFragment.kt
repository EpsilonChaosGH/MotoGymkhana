package com.example.motogymkhana.screens.championships

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.Const
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentChampionshipsBinding
import com.example.motogymkhana.model.ChampionshipState
import com.example.motogymkhana.utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChampionshipsFragment : Fragment(R.layout.fragment_championships) {

    private val binding by viewBinding(FragmentChampionshipsBinding::class.java)

    private val viewModel by viewModels<ChampionshipsViewModel>()

    private val adapter = ChampionshipAdapter(object : ChampionshipsListener{
        override fun showStagesList(championship: ChampionshipState) {
            findNavController().navigate(
                R.id.action_championshipsFragment_to_stagesFragment,
                bundleOf(Const.CHAMPIONSHIPS_ID_KEY to championship)
            )
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        observeStagesState()
    }

    private fun observeStagesState() = with(binding) {
        collectFlow(viewModel.uiState) { state ->

            adapter.items = state.championships
            progressBar.isVisible = state.isLoading

            state.userMessage.get()?.let {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }
}