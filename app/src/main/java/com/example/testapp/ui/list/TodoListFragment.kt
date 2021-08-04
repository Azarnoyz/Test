package com.example.testapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.FragmentTodoListBinding
import com.example.testapp.ui.MainActivity
import com.example.testapp.ui.details.TodoListDetailsFragment
import com.example.testapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoListFragment : Fragment() {

    private val todoListViewModel: TodoListViewModel by viewModels()
    private lateinit var adapter: TodoListAdapter

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initObservers()
        init()

    }

    private fun initObservers() {
        todoListViewModel.todoListData.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    showList()
                    it.data?.let { list ->
                        adapter.submitList(list)
                    }
                }
                Status.LOADING -> {
                    hideList()
                }
                Status.ERROR -> {
                    todoListViewModel.getTodoListsFromDb()
                    showList()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        todoListViewModel.todoListDataFromDB.observe(requireActivity(), {
            showList()
            adapter.submitList(it)
        })
    }

    private fun initAdapter() = with(binding) {
        adapter = TodoListAdapter {
            (activity as MainActivity).loadFragment(
                TodoListDetailsFragment.newInstance(
                    it.title,
                    it.date
                )
            )
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun showList() = with(binding) {
        progress.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun hideList() = with(binding) {
        progress.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun init() {
        (activity as MainActivity).networkConnectionListener {
            if (it) {
                todoListViewModel.getTodoLists()
            } else {
                todoListViewModel.getTodoListsFromDb()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}