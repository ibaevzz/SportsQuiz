package com.ibaevzz.sportsquiz

import android.app.Application
import androidx.room.Room
import com.ibaevzz.sportsquiz.db.Question
import com.ibaevzz.sportsquiz.db.QuestionDatabase

class App: Application() {

    lateinit var db: QuestionDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, QuestionDatabase::class.java, "question").build()
        Thread {
            db.getQuestionDao().addQuestion(
                Question(
                    0, "Что такое бег", listOf(
                        "Когда человек машет руками и разгоняется",
                        "Что-то типо жима лежа", "Оба варианта не верны"
                    ), 0, 1
                )
            )
            db.getQuestionDao().addQuestion(
                Question(
                    1, "Рекорд на жиме лежа Рони Коллмана", listOf(
                        "200",
                        "240", "400"
                    ), 1, 1
                )
            )
            db.getQuestionDao().addQuestion(
                Question(
                    2, "Что такое бег", listOf(
                        "Когда человек машет руками и разгоняется",
                        "Что-то типо жима лежа", "Оба варианта не верны"
                    ), 0, 3
                )
            )
            db.getQuestionDao().addQuestion(
                Question(
                    3, "Рекорд на жиме лежа Рони Коллмана", listOf(
                        "200",
                        "240", "400"
                    ), 1, 3
                )
            )
            db.getQuestionDao().addQuestion(
                Question(
                    4, "Что такое бег", listOf(
                        "Когда человек машет руками и разгоняется",
                        "Что-то типо жима лежа", "Оба варианта не верны"
                    ), 0, 2
                )
            )
            db.getQuestionDao().addQuestion(
                Question(
                    5, "Рекорд на жиме лежа Рони Коллмана", listOf(
                        "200",
                        "240", "400"
                    ), 1, 2
                )
            )
        }.start()
    }
}