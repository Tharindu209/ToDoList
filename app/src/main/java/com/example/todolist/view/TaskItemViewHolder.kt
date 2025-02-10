package com.example.todolist.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.TaskItemCellBinding
import com.example.todolist.model.TaskItem
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")


    private fun applyColoredStrikeThrough(textView: TextView, text: String, color: Int) {
        val spannable = SpannableString(text).apply {
            setSpan(StrikethroughSpan(), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(color), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView.text = spannable
    }

    private fun clearStrikeThrough(textView: TextView, text: String) {
        textView.text = text // Reset to original text without spans
    }

    fun bindTaskItem(taskItem: TaskItem) {

        binding.name.text = taskItem.name
        binding.desc.text = taskItem.desc
        if (taskItem.dueTime() != null) {
            binding.dueTime.text = timeFormat.format(taskItem.dueTime())
        } else {
            binding.dueTime.text = ""
        }

        binding.completeButton.setOnClickListener {
            clickListener.toggleTaskItemCompletion(taskItem)
        }
        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        if (taskItem.isCompleted()) {
//            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//            binding.desc.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            val strikeColor = ContextCompat.getColor(binding.root.context, R.color.orange)
            applyColoredStrikeThrough(binding.name, taskItem.name, strikeColor)
            applyColoredStrikeThrough(binding.desc, taskItem.desc, strikeColor)
            binding.dueTime.setTextColor(ContextCompat.getColor(context, R.color.fade))
        } else {
            clearStrikeThrough(binding.name, taskItem.name)
            clearStrikeThrough(binding.desc, taskItem.desc)
            binding.dueTime.setTextColor(Color.BLACK)
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

    }
}