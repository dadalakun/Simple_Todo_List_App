package com.example.todolist.addtodo

import android.app.DatePickerDialog
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
import com.example.todolist.databinding.FragmentAddTodoBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

        binding.submitButton.setOnClickListener {
            viewModel.onFinish(binding.todoTitleInput.text.toString(),
            binding.todoDetailInput.text.toString())
        }

        /** Setup LiveData observation relationship **/
        viewModel.duedate.observe(viewLifecycleOwner, Observer { newduedate ->
            binding.todoDuedateInput.text = newduedate
        })

        return binding.root
    }

}