package com.example.testapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ItemTodolistBinding
import com.example.testapp.model.TodoListData

class TodoListAdapter(
    private val listener: (TodoListData) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<TodoListData>() {
        override fun areItemsTheSame(oldItem: TodoListData, newItem: TodoListData): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TodoListData, newItem: TodoListData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<TodoListData>) = differ.submitList(list)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(ItemTodolistBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ItemTodolistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todoTodoListData: TodoListData) = with(binding) {

            tvTitle.text = todoTodoListData.title
        }
    }


}