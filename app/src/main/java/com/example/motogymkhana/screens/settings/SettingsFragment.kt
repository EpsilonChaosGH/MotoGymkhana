package com.example.motogymkhana.screens.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.motogymkhana.R
import com.example.motogymkhana.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::class.java)

    @Inject
    lateinit var settings: SettingsSharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        ipEditText.setText(settings.getControllerIp())
        requestEditText.setText(settings.getRequest())

        saveSettingsButton.setOnClickListener {
            settings.setControllerIp(ipEditText.text.toString())
            settings.setRequest(requestEditText.text.toString())
            findNavController().navigateUp()
        }
    }
}