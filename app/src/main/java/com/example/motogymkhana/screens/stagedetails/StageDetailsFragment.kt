package com.example.motogymkhana.screens.stagedetails

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.Const
import com.example.motogymkhana.R
import com.example.motogymkhana.data.StopWatchService
import com.example.motogymkhana.databinding.FragmentStageDetailsBinding
import com.example.motogymkhana.model.Attempt
import com.example.motogymkhana.model.IsActive
import com.example.motogymkhana.model.PostTimeRequestBody
import com.example.motogymkhana.model.Type
import com.example.motogymkhana.model.UserResultState
import com.example.motogymkhana.model.UserStatus
import com.example.motogymkhana.utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class StageDetailsFragment : Fragment(R.layout.fragment_stage_details) {

    private val binding by viewBinding(FragmentStageDetailsBinding::class.java)

    private val viewModel by viewModels<StageDetailsViewModel>()

    private lateinit var adapter: UserAdapter

    @Inject
    lateinit var stopWatchService: StopWatchService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val stageId = requireArguments().getLong(Const.STAGE_ID_KEY)

        adapter = UserAdapter(object : UserListener {

            override fun showMenu(user: UserResultState, attempt: Attempt) {
                menu(user, attempt)
            }

            override fun setActive(isActive: IsActive, participantID: Long) {
                viewModel.setActive(isActive, participantID)
            }
            override fun closeMenu() {
                menuConstraintLayout.isVisible = false
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        refreshLayout.setColorSchemeResources(R.color.onBackground)
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary)
        refreshLayout.setOnRefreshListener { viewModel.refresh() }

        viewModel.getStageInfo(id = stageId.toString(), type = Type.Offline.value)

        timeSettingsImageView.setOnClickListener {
            findNavController().navigate(R.id.action_stageDetailsFragment_to_settingsFragment)
        }

        currentTimeTextView.setOnClickListener {
            findNavController().navigate(R.id.action_stageDetailsFragment_to_stopWatchFragment)
        }

        stopWatchService.getTimeFlow()
            .onEach {
                requireActivity().runOnUiThread {
                    currentTimeTextView.text = it
                }
            }
            .catch { Log.e("aaa_catch", it.message.toString()) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        observeStageState()
    }

    private fun menu(user: UserResultState, attempt: Attempt) = with(binding) {
        isFailSwitch.isChecked = false
        timePicker.setValue(0)
        menuConstraintLayout.isVisible = true
        nameTextView.text = user.userFullName
        numberTextView.text = user.number
        timeInputText.hint = attempt.title

        when (user.userStatus) {
            UserStatus.FINISHED -> {
                numberTextView.setTextColor(Color.WHITE)
                numberTextView.setBackgroundResource(user.userStatus.colorResId)
            }

            UserStatus.RIDES -> {
                numberTextView.setTextColor(Color.WHITE)
                numberTextView.setBackgroundResource(user.userStatus.colorResId)
            }

            UserStatus.NEXT -> {
                numberTextView.setTextColor(Color.WHITE)
                numberTextView.setBackgroundResource(user.userStatus.colorResId)
            }

            UserStatus.HEATING -> {
                numberTextView.setTextColor(Color.BLACK)
                numberTextView.setBackgroundResource(user.userStatus.colorResId)
            }

            UserStatus.WAITING -> {
                numberTextView.setTextColor(Color.BLACK)
                numberTextView.setBackgroundResource(user.userStatus.colorResId)
            }
        }

        when (attempt) {
            Attempt.First -> {
                timeEditText.setText(user.attempts.getOrNull(0)?.resultTime)
            }

            Attempt.Second -> {
                timeEditText.setText(user.attempts.getOrNull(1)?.resultTime)
            }
        }

        timeButton.setOnClickListener {
            timeEditText.setText(currentTimeTextView.text)
        }

        saveButton.setOnClickListener {
            viewModel.uiState.value?.let { stage ->
                viewModel.postTime(
                    PostTimeRequestBody(
                        stageId = stage.stageState?.stageID.toString(),
                        participantID = user.participantID.toString(),
                        attempt = attempt.value,
                        time = timeEditText.text.toString(),
                        fine = timePicker.getValue().toString(),
                        isFail = if (isFailSwitch.isChecked) {
                            "1"
                        } else {
                            "0"
                        }
                    )
                )
            }
        }
    }

    private fun observeStageState() = with(binding) {
        collectFlow(viewModel.uiState) { state ->

            state.stageState?.let {
                adapter.items = it.results
            }

            refreshLayout.isRefreshing = state.isLoading
            state.userMessage.get()?.let {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }
}