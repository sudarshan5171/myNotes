package com.example.mynotes.UI.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.Model.Notes
import com.example.mynotes.R
import com.example.mynotes.UI.Adapters.NotesAdapter
import com.example.mynotes.ViewModel.NotesViewModel
import com.example.mynotes.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        //set layout as staggeredview
        val staggeredView = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.rvAllNotes.layoutManager = staggeredView

        //get all notes
        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            oldMyNotes = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(), notesList)
            binding.rvAllNotes.adapter = adapter
        }

        //onclick for add notes button
        binding.addNotesBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_addNotesFragment)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Search your notes.."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false;
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                notesFiltering(p0)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun notesFiltering(p0: String?) {
        val newFilteredList = arrayListOf<Notes>()

        for(i in oldMyNotes){
            if(i.title.contains(p0!!) || i.subTitle.contains(p0!!)){
                newFilteredList.add(i)
            }
        }
        adapter.filtering(newFilteredList)
    }
}