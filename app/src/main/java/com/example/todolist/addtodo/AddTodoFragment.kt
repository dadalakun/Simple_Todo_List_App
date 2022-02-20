package com.example.todolist.addtodo

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddTodoBinding
import java.util.*

/**
 * Fragment where user input information and create a todo
 */
class AddTodoFragment : Fragment() {

    private lateinit var binding: FragmentAddTodoBinding

    private lateinit var viewModel: AddTodoViewModel

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

        viewModel = ViewModelProvider(this).get(AddTodoViewModel::class.java)

        /**
         * Add listener
         */
        // Date picker button
        binding.pickDateButton.setOnClickListener {
//            DatePickerFragment().show(parentFragmentManager, "datePicker")
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            // we have to implement setFragmentResultListener
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
//                    tvSelectedDate.text = date
                    binding.todoDuedateInput.text = date
                }
            }

            // show
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }
        // Submit button
        binding.submitButton.setOnClickListener {
            findNavController().navigate(AddTodoFragmentDirections.actionAddTodoFragmentToHomeFragment())
        }

        return binding.root
    }
    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year-${month + 1}-$day"
    }
}