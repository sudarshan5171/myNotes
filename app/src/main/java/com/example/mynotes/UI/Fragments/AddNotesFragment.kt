package com.example.mynotes.UI.Fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mynotes.Model.Notes
import com.example.mynotes.R
import com.example.mynotes.ViewModel.NotesViewModel
import com.example.mynotes.databinding.FragmentAddNotesBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNotesFragment : Fragment() {
    lateinit var binding: FragmentAddNotesBinding
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

        binding = FragmentAddNotesBinding.inflate(layoutInflater,container,false)

        binding.saveNotesBtn.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    fun createNotes(it: View?) {

        val currtitle = binding.editTitle.text.toString()
        val currsubTitle = binding.editSubTitle.text.toString()
        val currnote = binding.editDescription.text.toString()

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
        val currDate = formatter.format(date)

        val data = Notes(id=null,title=currtitle, subTitle =currsubTitle, date = currDate, description = currnote)
        viewModel.addNotes(data)

        Navigation.findNavController(it!!).navigate(R.id.action_addNotesFragment_to_homeFragment)
    }

}