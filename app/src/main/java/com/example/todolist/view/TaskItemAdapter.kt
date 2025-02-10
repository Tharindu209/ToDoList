package com.example.todolist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TaskItemCellBinding
import com.example.todolist.model.TaskItem

class TaskItemAdapter(
    private var taskItems: List<TaskItem>,
    private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val from = LayoutInflater.from((parent.context))
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskItemViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int = taskItems.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

    fun removeTask(position: Int): TaskItem {
        val taskToRemove = taskItems[position]
        taskItems = taskItems.toMutableList().apply { removeAt(position) }  // Create a new list
        notifyItemChanged(position)
        return taskToRemove
    }

    fun restoreTask(task: TaskItem, position: Int) {
        taskItems = taskItems.toMutableList().apply { add(position, task) }  // Create a new list
        notifyItemChanged(position)
    }
}