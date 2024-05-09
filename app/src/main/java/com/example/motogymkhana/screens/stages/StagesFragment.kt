package com.example.motogymkhana.screens.stages

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
import com.example.motogymkhana.databinding.FragmentStagesBinding
import com.example.motogymkhana.model.ChampionshipState
import com.example.motogymkhana.model.Type
import com.example.motogymkhana.utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StagesFragment : Fragment(R.layout.fragment_stages) {

    private val binding by viewBinding(FragmentStagesBinding::class.java)

    private val viewModel by viewModels<StagesViewModel>()

    private var championshipId: ChampionshipState? = null

    private val adapter = StageAdapter(object : StageListener {
        override fun showStageDetails(id: Long) {

            findNavController().navigate(
                R.id.action_stagesFragment_to_stageDetailsFragment,
                bundleOf(Const.STAGE_ID_KEY to id)
            )
        }

        override fun addStageIdToFavorites(id: Long) {
            viewModel.addStageIdToFavorites(id)
        }

        override fun deleteFromFavoritesByStageId(id: Long) {
            viewModel.deleteFromFavoritesByStageId(id)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        championshipId = requireArguments().getParcelable(Const.CHAMPIONSHIPS_ID_KEY)

        championshipId?.let {
            viewModel.loadStages(championshipId = it.id, type = Type.Offline.value)
            textView.text = it.title
        }

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