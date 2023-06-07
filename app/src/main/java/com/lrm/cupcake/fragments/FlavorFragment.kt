package com.lrm.cupcake.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.lrm.cupcake.databinding.FragmentFlavorBinding
import com.lrm.cupcake.model.OrderViewModel

class FlavorFragment : Fragment() {

    private var _binding: FragmentFlavorBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlavorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIcon.setOnClickListener {
            val action = FlavorFragmentDirections.actionFlavorFragmentToStartFragment()
            findNavController().navigate(action)
        }

        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.nextButton.setOnClickListener {
            val action = FlavorFragmentDirections.actionFlavorFragmentToPickupFragment()
            findNavController().navigate(action)
        }

        binding.cancelButton.setOnClickListener {
            sharedViewModel.resetOrder()
            val action = FlavorFragmentDirections.actionFlavorFragmentToStartFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}