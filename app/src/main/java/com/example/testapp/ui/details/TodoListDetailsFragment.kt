package com.example.testapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentTodoListDetailsBinding
import com.example.testapp.utils.formatTo
import com.example.testapp.utils.toDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoListDetailsFragment : Fragment() {

    companion object {

        private const val TITLE_KEY = "title_key"
        private const val DATE_KEY = "date_key"

        fun newInstance(title: String, date: String) =
            TodoListDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE_KEY, title)
                    putString(DATE_KEY, date)
                }
            }
    }

    private val title: String by lazy { arguments?.getString(TITLE_KEY, "") ?: "" }
    private val date: String by lazy {
        arguments?.getString(DATE_KEY, "")?.toDate()?.formatTo("dd MMM yyyy hh:mm") ?: ""
    }

    private var _binding: FragmentTodoListDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        titleTv.text = title
        dateTv.text = date
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}