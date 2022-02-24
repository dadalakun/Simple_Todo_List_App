package com.example.todolist.addtodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.convertDateToString
import com.example.todolist.database.TodoDatabase
import com.example.todolist.databinding.FragmentAddTodoBinding
import java.util.*

/**
 * Fragment where user input information and create todo
 */
class AddTodoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentAddTodoBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_todo, container,false)

        val application = requireNotNull(this.activity).application
        // Get the reference to the DAO of the database
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = AddTodoViewModelFactory(dataSource, application)
        val addTodoViewModel = ViewModelProvider(
            this, viewModelFactory).get(AddTodoViewModel::class.java)

        binding.addTodoViewModel = addTodoViewModel

        binding.setLifecycleOwner(this)

        // Use DatePickerFragment to help user selecting the date
        // ref: https://github.com/chankruze/DatePickerDialogFragment
        binding.dueDateButton.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getSerializable("SELECTED_DATE")
                    addTodoViewModel.due_date.value = date as Date?
                }
            }

            // pop out the fragment
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        binding.createdDateButton.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getSerializable("SELECTED_DATE")
                    addTodoViewModel.created_date.value = date as Date?
                }
            }

            // show
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        binding.submitButton.setOnClickListener {
            // Checking  whether the title and the description are valid value
            val title = binding.todoTitleInput.text.toString().trim()
            val description = binding.todoDetailInput.text.toString().trim()
            if(title.isEmpty() || description.isEmpty()) {
                Toast.makeText(context, "Missing title or description", Toast.LENGTH_SHORT).show()
            } else {
                addTodoViewModel.submit(title, description)
                addTodoViewModel.onCreated()
            }
        }

        // Add Observers for date button/reload button/navigation
        addTodoViewModel.due_date.observe(viewLifecycleOwner, Observer { new_due_date ->
            binding.dueDateButton.text = convertDateToString(new_due_date)
        })
        addTodoViewModel.created_date.observe(viewLifecycleOwner, Observer { new_created_date ->
            binding.createdDateButton.text = convertDateToString(new_created_date)
        })
        addTodoViewModel.location.observe(viewLifecycleOwner, Observer { new_location ->
            binding.locationInput.text = new_location
        })

        addTodoViewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    AddTodoFragmentDirections.actionAddTodoFragmentToHomeFragment()
                )
                addTodoViewModel.doneNavigation()
            }
        })

        return binding.root
    }

}