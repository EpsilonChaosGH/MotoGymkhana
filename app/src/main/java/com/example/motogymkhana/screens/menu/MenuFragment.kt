package com.example.motogymkhana.screens.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.Const
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentMenuBinding
import com.example.motogymkhana.utils.collectFlow
import com.example.motogymkhana.screens.stages.StageAdapter
import com.example.motogymkhana.screens.stages.StageListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::class.java)

    private val viewModel by viewModels<MenuViewModel>()

    private val adapter = StageAdapter(object : StageListener {
        override fun showStageDetails(id: Long) {
            findNavController().navigate(
                R.id.action_menuFragment_to_stageDetailsFragment,
                bundleOf(Const.STAGE_ID_KEY to id)
            )
        }

        override fun addStageIdToFavorites(id: Long) {}

        override fun deleteFromFavoritesByStageId(id: Long) {
            viewModel.deleteFromFavoritesByStageId(id)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        openChampionshipsButton.setOnClickListener {
            openChampionships()
        }

//        openTestButton.setOnClickListener {
//            openTest()
//        }

        observeStagesState()
    }

    private fun observeStagesState() = with(binding) {
        collectFlow(viewModel.uiState) { state ->

            adapter.items = state.stages
            progressBar.isVisible = state.isLoading

            recyclerView.isInvisible = state.stages.isEmpty()
            favoritesTextView.isInvisible = state.stages.isEmpty()

            state.userMessage.get()?.let {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openChampionships(){
        findNavController().navigate(R.id.action_menuFragment_to_championshipsFragment)
    }

    private fun openTest(){
        findNavController().navigate(R.id.action_menuFragment_to_fragmentTest)
    }
}