package com.todolist.app

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todolist.app.databinding.ItemTaskBinding

class TaskAdapter(
    private val onToggle: (Task) -> Unit,
    private val onDelete: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.VH>(DIFF) {

    inner class VH(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val task = getItem(position)
        with(holder.binding) {
            checkbox.isChecked = task.isDone
            tvTitle.text = task.title
            tvTitle.paintFlags = if (task.isDone)
                tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else
                tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            checkbox.setOnClickListener { onToggle(task) }
            btnDelete.setOnClickListener { onDelete(task) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(a: Task, b: Task) = a.id == b.id
            override fun areContentsTheSame(a: Task, b: Task) = a == b
        }
    }
}
