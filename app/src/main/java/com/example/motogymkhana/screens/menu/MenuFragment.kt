package com.example.motogymkhana.screens.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::class.java)

//    private val viewModel by viewModels<FavoritesViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        openStageButton.setOnClickListener {
            openStage()
        }
    }

    private fun openStage(){
        findNavController().navigate(R.id.action_menuFragment_to_stageFragment)
    }

}