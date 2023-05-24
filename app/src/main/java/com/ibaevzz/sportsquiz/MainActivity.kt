package com.ibaevzz.sportsquiz

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ibaevzz.sportsquiz.databinding.ActivityMainBinding
import com.ibaevzz.sportsquiz.databinding.LevelDialogBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: LevelDialogBinding
    private lateinit var dialog: Dialog
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customMainView()
        customDialog()
    }

    override fun onResume() {
        super.onResume()
        binding.score.text = pref.getInt("score", 0).toString()
    }

    private fun customMainView(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.play.setOnClickListener{
            dialog.show()
        }
        binding.wallpaper.setOnClickListener{
            startActivity(Intent(this, WallpaperActivity::class.java))
        }
        pref = getSharedPreferences("main_pref", MODE_PRIVATE)
        binding.score.text = pref.getInt("score", 0).toString()
    }

    private fun customDialog(){
        dialogBinding = LevelDialogBinding.inflate(layoutInflater)
        dialogBinding.firstLevel.setOnClickListener{
            startGame(1)
        }
        dialogBinding.secondLevel.setOnClickListener{
            startGame(2)
        }
        dialogBinding.hardLevel.setOnClickListener{
            startGame(3)
        }
        dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)
    }

    private fun startGame(level: Int){
        val intent = Intent(this, PlayActivity::class.java)
        intent.putExtra("level", level)
        dialog.dismiss()
        startActivity(intent)
    }
}