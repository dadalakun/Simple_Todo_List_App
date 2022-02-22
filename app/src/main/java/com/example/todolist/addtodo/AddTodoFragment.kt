package com.example.todolist.addtodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.database.TodoDatabase
import com.example.todolist.databinding.FragmentAddTodoBinding

/**
 * Fragment where user input information and create a todo
 */
class AddTodoFragment : Fragment() {

    private lateinit var binding: FragmentAddTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_todo,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        // Get the reference to the DAO of the database
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = AddTodoViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(AddTodoViewModel::class.java)

        /** Setup listener **/
        // ref: https://github.com/chankruze/DatePickerDialogFragment
        binding.pickDateButton.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
                    viewModel.duedate.value = date
                }
            }

            // show
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
                    val date = bundle.getString("SELECTED_DATE")
                    viewModel.created_date.value = date
                }
            }

            // show
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        binding.submitButton.setOnClickListener {
            viewModel.submit(binding.todoTitleInput.text.toString(),
            binding.todoDetailInput.text.toString())
            findNavController().navigate(AddTodoFragmentDirections.actionAddTodoFragmentToHomeFragment())
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigate(AddTodoFragmentDirections.actionAddTodoFragmentToHomeFragment())
        }

        /** Setup LiveData observation relationship **/
        viewModel.duedate.observe(viewLifecycleOwner, Observer { new_duedate ->
            binding.pickDateButton.text = new_duedate
        })

        return binding.root
    }

}