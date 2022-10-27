package com.metehanbolat.darkthemeimplementation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.metehanbolat.darkthemeimplementation.ThemeChanger
import com.metehanbolat.darkthemeimplementation.databinding.FragmentFirstBinding
import kotlinx.coroutines.launch

class FirstFragment: Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            ThemeChanger(requireContext()).setUIModel()
        }
        lifecycleScope.launch {
            ThemeChanger(requireContext()).uiMode.collect {
                binding.darkModeCheckBox.isChecked = it
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.firstFragmentButton.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment()
            findNavController().navigate(action)
        }

        binding.darkModeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                ThemeChanger(requireContext()).saveToDataStore(requireContext(), isChecked)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}