package com.ibaevzz.sportsquiz.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(@PrimaryKey(autoGenerate = true) val id: Long,
                    val question: String,
                    val answers: List<String>,
                    val correctAnswer: Int,
                    val level: Int)