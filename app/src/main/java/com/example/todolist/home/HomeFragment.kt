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

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        // Get the reference to the DAO of the database
        val dataSource = TodoDatabase.getInstance(application).todoDatabaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(HomeViewModel::class.java)

        /** Setup listener **/
        binding.addTodoButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddTodoFragment())
        }

        /** Setup LiveData observation relationship **/
//        viewModel.todosString.observe(viewLifecycleOwner, Observer { todos ->
//            binding.testTextfield.text = todos
//        })

        /** Setup adapter **/
        val adapter = TodoAdapter()
        binding.todoList.adapter = adapter
        viewModel.todos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }

}