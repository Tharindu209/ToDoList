package com.example.todolist.model

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "task_item_table")
class TaskItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "desc") var desc: String,
    @ColumnInfo(name = "dueTimeString") var dueTimeString: String?,
    @ColumnInfo(name = "completedDateString") var completedDateString: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {

    private fun completedDate(): LocalDate? = if (completedDateString == null) null
    else LocalDate.parse(completedDateString, dateFormatter)

    fun dueTime(): LocalTime? = if (dueTimeString == null) null
    else LocalTime.parse(dueTimeString, timeFormatter)

    fun isCompleted() = completedDate() != null
    fun imageResource(): Int = if (isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24

    private fun doneColor(context: Context) = ContextCompat.getColor(context, R.color.orange)
    private fun notDoneColor(context: Context) = ContextCompat.getColor(context, R.color.black)
    fun imageColor(context: Context): Int =
        if (isCompleted()) doneColor(context) else notDoneColor(context)


    companion object {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE

    }
}