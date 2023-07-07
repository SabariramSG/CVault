package com.example.cvault

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cvault.adapter.CardListAdapter
import com.example.cvault.bo.CardDetails
import com.example.cvault.databinding.FragmentHomeBinding
import com.example.cvault.viewmodel.CardDetailsViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUpData()
    }

    private fun setUpData() {
        /*updateCardList(
            listOf(
                listOf("Card 1"),
                listOf("Card 2")
            )
        )*/
        ProgressDialogUtil.showLoadingDialog(activity,"Fetching card details")
        viewModel.fetchCards().observe(viewLifecycleOwner) {
            ProgressDialogUtil.dismissLoadingDialog(activity)
            if(!it.isNullOrEmpty())
                updateCardList(it)
            else
                Toast.makeText(activity,"No cards available",Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateCardList(list: List<CardDetails>?) {
        binding.apply {
            listRv.apply {
                this.adapter = CardListAdapter(list,
                    onItemClicked = {
                        val bundle = Bundle()
                        bundle.putString(Constants.SELECTED_CARD,it)
                        arguments = bundle
                        findNavController().navigate(R.id.action_homeFragment_to_cardPreviewFragment,arguments)
                    })
                this.layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}