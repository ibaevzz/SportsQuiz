package com.ibaevzz.sportsquiz

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibaevzz.sportsquiz.databinding.ActivityPlayBinding
import com.ibaevzz.sportsquiz.databinding.AnswersBinding
import com.ibaevzz.sportsquiz.db.Question
import java.util.Random
import kotlin.concurrent.thread

class PlayActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPlayBinding
    private lateinit var list: List<Question>
    private var isRun = false
    private var isFirst = true
    private var thread: Thread? = null
    private var num: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading)
        binding = ActivityPlayBinding.inflate(layoutInflater)

        val level = intent.getIntExtra("level", 1)
        (applicationContext as App).db.getQuestionDao().getQuestions(level).observe(this){
            if(it.isEmpty()){
                Toast.makeText(this, "Вопросов нет", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                list = it
                setContentView(binding.root)
                binding.next.setOnClickListener{
                    makeQuestion()
                }
                makeQuestion()
            }
        }
    }

    private fun makeQuestion(){
        num+=1
        isFirst = true
        isRun = true
        val i = (list.indices).random()

        binding.question.text = list[i].question

        binding.answers.layoutManager = LinearLayoutManager(this)
        binding.answers.adapter = Adapter(list[i].answers, list[i].correctAnswer)

        thread = Thread{
            var time = 600
            val n = num
            while(isRun&&n==num){
                Thread.sleep(100)
                time-=1
                runOnUiThread{
                    binding.progress.progress = time/6
                }
                if(time==0){
                    isRun = false
                }
            }
        }
        thread!!.start()
    }

    private inner class Holder(private val answersBinding: AnswersBinding,
                               private val correct: Int): RecyclerView.ViewHolder(answersBinding.root){
        @SuppressLint("ResourceAsColor")
        fun bind(str: String, i: Int){
            answersBinding.answer.text = str
            answersBinding.frameBack.setOnClickListener{
                isRun = if(correct==i){
                    answersBinding.frameBack.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
                    if(isFirst){
                        val pref = getSharedPreferences("main_pref", MODE_PRIVATE)
                        val score = pref.getInt("score", 0)
                        pref.edit().putInt("score", score+1).apply()
                        Toast.makeText(this@PlayActivity, "Верно! +1 очко", Toast.LENGTH_SHORT).show()
                    }
                    isFirst = false
                    false
                }else{
                    answersBinding.frameBack.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
                    isFirst = false
                    false
                }
            }
        }
    }
    private inner class Adapter(private val list: List<String>, private val correct: Int): RecyclerView.Adapter<Holder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(AnswersBinding.inflate(LayoutInflater.from(parent.context)), correct)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(list[position], position)
        }
    }
}