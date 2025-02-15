package com.example.todolist.view

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.FragmentnewtasksheetBinding
import com.example.todolist.model.TaskItem
import com.example.todolist.viewmodel.TaskViewModel
import java.time.LocalTime

class NewTaskSheet(var taskItem: TaskItem?) : DialogFragment() {

    private lateinit var binding: FragmentnewtasksheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentnewtasksheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null) {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)

            if (taskItem!!.dueTime() != null) {
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        } else {
            binding.taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)

        binding.saveButton.setOnClickListener { saveAction() }
        binding.timePickerButton.setOnClickListener { openTimePicker() }
    }

    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }

        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout((resources.displayMetrics.widthPixels * 0.9).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    private fun saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()

        if (name.isEmpty()) {
            binding.name.error = "Task name cannot be empty"
            return
        }

        if (desc.isEmpty()) {
            binding.desc.error = "Task description cannot be empty"
            return
        }

        val dueTimeString = if (dueTime == null) null else TaskItem.timeFormatter.format(dueTime)

        if (taskItem == null) {
            val newTask = TaskItem(name, desc, dueTimeString, null)
            taskViewModel.addTaskItem(newTask)
        } else {
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString
            taskViewModel.updateTaskItem(taskItem!!)
        }

        dismiss()
    }
}
