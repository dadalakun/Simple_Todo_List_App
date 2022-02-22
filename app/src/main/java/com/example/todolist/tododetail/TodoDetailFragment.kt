package com.example.todolist.tododetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.database.TodoDatabase
import com.example.todolist.databinding.FragmentTodoDetailBinding

class TodoDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentTodoDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_todo_detail, container,false)

        val application = requireNotNull(this.activity).application
//        val arguments = TodoDetailFragmentArgs.fromBundle(arguments)
        val args: TodoDetailFragmentArgs by navArgs()

        // Create an instance of the ViewModel Factory.
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
//        val viewModelFactory = TodoDetailViewModelFactory(arguments.todoKey, dataSource)
        val viewModelFactory = TodoDetailViewModelFactory(args.todoKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val todoDetailViewModel = ViewModelProvider(
            this, viewModelFactory).get(TodoDetailViewModel::class.java)

        // To use the View Model with data binding, explicitly give the binding
        // object a reference to it.
        binding.todoDetailViewModel = todoDetailViewModel

        binding.setLifecycleOwner(this)

        // Add an Observer to the state variable for Navigating when the "back" button is tapped.
        todoDetailViewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    TodoDetailFragmentDirections.actionTodoDetailFragmentToHomeFragment()
                )
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                todoDetailViewModel.doneNavigation()
            }
        })

        return binding.root
    }
}