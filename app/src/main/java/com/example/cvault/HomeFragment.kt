package com.example.cvault

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cvault.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

    private fun setUpUI() {
        updateCardList(
            listOf(
                listOf("Card 1"),
                listOf("Card 2")
            )
        )
    }
    private fun updateCardList(list: List<List<String>>?) {
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