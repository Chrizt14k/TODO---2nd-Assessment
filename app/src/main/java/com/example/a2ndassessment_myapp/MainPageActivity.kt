// Main page

package com.example.a2ndassessment_myapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainPageActivity : AppCompatActivity() {

    private lateinit var taskInput: EditText
    private lateinit var addTaskButton: Button
    private lateinit var taskRecyclerView: RecyclerView

    private val taskList = mutableListOf<String>()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        // Link views
        taskInput = findViewById(R.id.taskInput)
        addTaskButton = findViewById(R.id.addTaskButton)
        taskRecyclerView = findViewById(R.id.taskRecyclerView)

        // Set up RecyclerView
        adapter = TaskAdapter(taskList)
        taskRecyclerView.adapter = adapter
        taskRecyclerView.layoutManager = LinearLayoutManager(this)

        // Add task button functionality
        addTaskButton.setOnClickListener {
            val task = taskInput.text.toString().trim()
            if (task.isNotEmpty()) {
                taskList.add(task)
                adapter.notifyItemInserted(taskList.size - 1)
                taskInput.text.clear()
            }
        }

        // Swipe to delete
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    taskList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            }
        })

        // Instruction popup
        val instructionButton: Button = findViewById(R.id.instructionButton)
        instructionButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("How to Use the To-Do List")
                .setMessage(
                    "1. Type your task in the input field.\n" +
                            "2. Tap 'Add' to add it to the list.\n" +
                            "3. Swipe left on any task to delete it.\n" +
                            "4. Scroll to view more tasks if needed."
                )
                .setPositiveButton("Got it", null)
                .show()
        }

        itemTouchHelper.attachToRecyclerView(taskRecyclerView)
    }
}
