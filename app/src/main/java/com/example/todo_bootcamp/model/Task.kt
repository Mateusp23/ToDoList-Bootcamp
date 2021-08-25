package com.example.todo_bootcamp.model

data class Task(
    val title: String,
    val hour: String,
    val date: String,
    val description: String,
    val id: Int = 0
)
