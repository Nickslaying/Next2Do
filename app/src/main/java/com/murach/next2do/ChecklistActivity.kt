package com.murach.next2do

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ChecklistActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var checklistContainer: LinearLayout
    private val checklistItems = mutableListOf<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)

        progressBar = findViewById(R.id.progressBar)
        checklistContainer = findViewById(R.id.checklistContainer)
        val addButton: Button = findViewById(R.id.addButton)

        addButton.setOnClickListener {
            showAddItemDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextItem)
        AlertDialog.Builder(this)
            .setTitle("+")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val itemText = editText.text.toString()
                if (itemText.isNotEmpty()) {
                    addChecklistItem(itemText)
                } else {
                    Toast.makeText(this, "Item cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addChecklistItem(itemText: String) {
        val checkBox = CheckBox(this).apply {
            text = itemText
            setOnCheckedChangeListener { _, _ ->
                updateProgressBar()
            }
        }
        checklistContainer.addView(checkBox)
        checklistItems.add(checkBox)
        updateProgressBar()
    }

    private fun updateProgressBar() {
        val totalItems = checklistItems.size
        if (totalItems == 0) {
            progressBar.progress = 0
            return
        }
        val completedItems = checklistItems.count { it.isChecked }
        val progressPercentage = (completedItems * 100) / totalItems
        progressBar.progress = progressPercentage
    }
}