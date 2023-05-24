package com.ibaevzz.sportsquiz.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(version = 1, entities = [Question::class])
@TypeConverters(Converter::class)
abstract class QuestionDatabase: RoomDatabase() {
    abstract fun getQuestionDao(): QuestionDao
}