package com.example.mynotes.UI.Fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.mynotes.Model.Notes
import com.example.mynotes.R
import com.example.mynotes.ViewModel.NotesViewModel
import com.example.mynotes.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class EditNotesFragment : Fragment() {
    val notes by navArgs<EditNotesFragmentArgs>()
    val viewModel: NotesViewModel by viewModels()
    lateinit var binding: FragmentEditNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNotesBinding.inflate(layoutInflater,container,false)

        setHasOptionsMenu(true)
        binding.editTitle.setText(notes.data.title)
        binding.editSubTitle.setText(notes.data.subTitle)
        binding.editDescription.setText(notes.data.description)

        binding.saveNotesBtn.setOnClickListener {
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?) {

        val currtitle = binding.editTitle.text.toString()
        val currsubTitle = binding.editSubTitle.text.toString()
        val currnote = binding.editDescription.text.toString()

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
        val currDate = formatter.format(date)

        val data = Notes(id=notes.data.id,title=currtitle, subTitle =currsubTitle, date = currDate, description = currnote)
        viewModel.updateNotes(data)

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment2)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.delete_menu){
            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.delete_dialog)
            bottomSheet.show()

            val yes = bottomSheet.findViewById<TextView>(R.id.delete_Yes)
            val no = bottomSheet.findViewById<TextView>(R.id.deleteNo)

            yes?.setOnClickListener {
                viewModel.deleteNotes(notes.data.id!!)
                bottomSheet.dismiss()
//                Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment2)
            }
            no?.setOnClickListener {
                bottomSheet.dismiss()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}