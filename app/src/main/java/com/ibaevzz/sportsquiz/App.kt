package com.ibaevzz.sportsquiz

import android.app.Application
import androidx.room.Room
import com.ibaevzz.sportsquiz.db.QuestionDatabase

class App: Application() {

    lateinit var db: QuestionDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, QuestionDatabase::class.java, "question").build()
    }
}