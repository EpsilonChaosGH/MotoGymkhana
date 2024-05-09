package com.example.motogymkhana.screens.stopwatch

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.R
import com.example.motogymkhana.data.StopWatchService
import com.example.motogymkhana.databinding.FragmentStopWatchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class StopWatchFragment : Fragment(R.layout.fragment_stop_watch) {

    private val binding by viewBinding(FragmentStopWatchBinding::class.java)

    @Inject
    lateinit var stopWatchService: StopWatchService

    override fun onStart() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stopWatchService.getTimeFlow()
            .onEach {
                requireActivity().runOnUiThread {
                    binding.currentTimeTextView.text = it
                }
            }
            .catch { Log.e("aaa_catch", it.message.toString()) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.timeSettingsImageView.setOnClickListener {
            findNavController().navigate(R.id.action_stopWatchFragment_to_settingsFragment)
        }
    }

    override fun onPause() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onPause()
    }
}