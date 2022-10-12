package com.example.mynotes.UI.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.R
import com.example.mynotes.UI.Adapters.NotesAdapter
import com.example.mynotes.ViewModel.NotesViewModel
import com.example.mynotes.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val staggeredView = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.rvAllNotes.layoutManager = staggeredView

        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            binding.rvAllNotes.adapter = NotesAdapter(requireContext(), notesList)
        }

        binding.addNotesBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_addNotesFragment)
        }
        return binding.root
    }
}