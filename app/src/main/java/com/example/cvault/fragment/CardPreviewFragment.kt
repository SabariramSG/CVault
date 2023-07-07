package com.example.cvault.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.cvault.Constants
import com.example.cvault.databinding.FragmentCardPreviewBinding
import com.example.cvault.viewmodel.CardDetailsViewModel

class CardPreviewFragment : Fragment() {

    private var _binding: FragmentCardPreviewBinding? = null
    private val binding get() = _binding!!

    private var cardName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCardPreviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        setUpUI()
    }

    private fun getBundleData() {
        cardName = arguments?.getString(Constants.SELECTED_CARD) ?: ""
    }

    private fun setUpUI() {
        binding.cardName.text = cardName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}