package com.example.todolist.home

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
import com.example.todolist.databinding.FragmentHomeBinding

/**
 * Fragment where todos are displayed
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container,false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        val homeViewModel = ViewModelProvider(
            this, viewModelFactory).get(HomeViewModel::class.java)

        // To use the View Model with data binding, explicitly give the binding
        // object a reference to it.
        binding.homeViewModel = homeViewModel

        // Setup adapter
        val adapter = TodoAdapter(TodoListener { todoId ->
            homeViewModel.onTodoClicked(todoId)
        })
        binding.todoList.adapter = adapter

        homeViewModel.todos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.setLifecycleOwner(this)

        // Add Observers on the state variable to track when to navigate
        homeViewModel.navigateToAddTodo.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAddTodoFragment()
                )
                homeViewModel.doneNavigation()
            }
        })
        homeViewModel.navigateToToDoDetail.observe(this, Observer { todoId ->
            todoId?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToTodoDetailFragment(todoId)
                )
                homeViewModel.onTodoDetailNavigated()
            }
        })

        return binding.root
    }

}