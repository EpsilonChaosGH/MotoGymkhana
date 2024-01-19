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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::class.java)
    private val myRef = Firebase.database.getReference(Const.TEST_DB_KEY)

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

        observeStagesState()

        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var str = ""
                val size = snapshot.children.toList().size
                snapshot.children.forEach {

                    val x = it.getValue(Test::class.java)
                    str = "$str + ${x?.testString}"
                }
               // Toast.makeText(requireContext(), "onDataChange $size", Toast.LENGTH_SHORT).show()

                //binding.textView.text = str
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "onCancelled!!", Toast.LENGTH_SHORT).show()

            }

        }

        myRef.addValueEventListener(listener)

//        saveButton.setOnClickListener {
//            saveData()
//        }

        openChampionshipsButton.setOnClickListener {
            openChampionships()
        }
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

    private fun saveData(){

//        myRef.push().setValue(Test(testString = binding.textView.text.toString()))
        myRef.push().updateChildren(mapOf("111" to Test(Random.nextInt(1000), "1-1")))
//        myRef.push().setValue(Test(testString = "TEXT"))
    }

    private fun openChampionships(){
        findNavController().navigate(R.id.action_menuFragment_to_championshipsFragment)
    }

}

data class Test(
    val testInt: Int = 12,
    val testString: String = "str"
)