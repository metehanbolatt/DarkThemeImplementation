package com.metehanbolat.darkthemeimplementation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.metehanbolat.darkthemeimplementation.R
import com.metehanbolat.darkthemeimplementation.ThemeChanger
import com.metehanbolat.darkthemeimplementation.databinding.FragmentBottomSheetBinding
import kotlinx.coroutines.launch

class BottomSheetFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            ThemeChanger(requireContext()).uiMode.collect {
                when(it) {
                    ThemeChanger.ThemeOption.DARK.option -> binding.darkButton.isChecked = true
                    ThemeChanger.ThemeOption.LIGHT.option -> binding.lightButton.isChecked = true
                    ThemeChanger.ThemeOption.SYSTEM.option -> binding.systemButton.isChecked = true
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.lightButton -> {
                    lifecycleScope.launch {
                        ThemeChanger(requireContext()).saveToDataStore(ThemeChanger.ThemeOption.LIGHT.option)
                    }
                }
                R.id.darkButton -> {
                    lifecycleScope.launch {
                        ThemeChanger(requireContext()).saveToDataStore(ThemeChanger.ThemeOption.DARK.option)
                    }
                }
                R.id.systemButton -> {
                    lifecycleScope.launch {
                        ThemeChanger(requireContext()).saveToDataStore(ThemeChanger.ThemeOption.SYSTEM.option)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}