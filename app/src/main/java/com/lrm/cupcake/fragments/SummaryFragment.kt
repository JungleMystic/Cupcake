package com.lrm.cupcake.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.lrm.cupcake.R
import com.lrm.cupcake.databinding.FragmentSummaryBinding
import com.lrm.cupcake.model.OrderViewModel

class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIcon.setOnClickListener {
            val action = SummaryFragmentDirections.actionSummaryFragmentToPickupFragment()
            findNavController().navigate(action)
        }

        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.sendButton.setOnClickListener {
            sendOrder()
        }

        binding.cancelButton.setOnClickListener {
            sharedViewModel.resetOrder()
            val action = SummaryFragmentDirections.actionSummaryFragmentToStartFragment()
            findNavController().navigate(action)
        }
    }

    private fun sendOrder() {
        val numberOfCupcakes = sharedViewModel.quantity.value ?: 0

        val orderSummary =
            getString(R.string.order_details,
                resources.getQuantityString(R.plurals.cupcakes, numberOfCupcakes, numberOfCupcakes),
                sharedViewModel.flavor.value.toString(),
                sharedViewModel.date.value.toString(),
                sharedViewModel.price.value.toString()
            )

        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cupcake_order))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)

        if (activity?.packageManager?.resolveActivity(intent, 0) != null){
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}