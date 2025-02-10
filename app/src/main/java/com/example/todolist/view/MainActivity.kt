package com.example.todolist.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.ToDoApplication
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.model.TaskItem
import com.example.todolist.viewmodel.TaskItemModelFactory
import com.example.todolist.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as ToDoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()

        setSwipeToDelete()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    private fun setSwipeToDelete() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val deletedTask =
                        (binding.todoListRecyclerView.adapter as TaskItemAdapter).removeTask(
                            position
                        )

                    // Delete task from ViewModel
                    taskViewModel.deleteTask(deletedTask)

                    // Show snackbar with undo option
                    Snackbar.make(
                        binding.todoListRecyclerView,
                        "Task deleted",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Undo") {
                            // Restore task to ViewModel
                            taskViewModel.undoDelete(deletedTask)

                            // Update RecyclerView
                            (binding.todoListRecyclerView.adapter as TaskItemAdapter).restoreTask(
                                deletedTask,
                                position
                            )
                        }.show()
                }
            }


        // Attach ItemTouchHelper to RecyclerView
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.todoListRecyclerView)
    }

    override fun toggleTaskItemCompletion(taskItem: TaskItem) {
        taskViewModel.toggleTaskCompleted(taskItem)
    }
}
