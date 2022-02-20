package com.example.todolist.addtodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddTodoBinding

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

        return binding.root
    }
}