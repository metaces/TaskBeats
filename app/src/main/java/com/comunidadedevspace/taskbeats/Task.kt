package com.comunidadedevspace.taskbeats

import java.io.Serializable

data class Task(
    val id: Int,
    val Title: String,
    val Description: String
): Serializable
