package com.todolist.app

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.todolist.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val adapter = TaskAdapter(onToggle = vm::toggle, onDelete = vm::delete)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        vm.tasks.observe(this) { tasks ->
            adapter.submitList(tasks)
            binding.tvEmpty.visibility = if (tasks.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            binding.recyclerView.visibility = if (tasks.isEmpty()) android.view.View.GONE else android.view.View.VISIBLE
        }

        fun addTask() {
            val text = binding.etTask.text?.toString()?.trim() ?: return
            if (text.isNotEmpty()) {
                vm.add(text)
                binding.etTask.text?.clear()
            }
        }

        binding.btnAdd.setOnClickListener { addTask() }
        binding.etTask.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { addTask(); true } else false
        }
    }
}
