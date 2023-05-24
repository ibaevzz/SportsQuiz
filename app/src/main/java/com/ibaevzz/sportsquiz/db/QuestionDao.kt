package com.ibaevzz.sportsquiz.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionDao {
    @Insert
    suspend fun addQuestion(question: Question)

    @Query("SELECT * FROM question WHERE level = :level")
    fun getQuestions(level: Int): LiveData<List<Question>>

    @Query("DELETE FROM question")
    suspend fun deleteAllQuestions()
}