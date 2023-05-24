package com.ibaevzz.sportsquiz

import android.app.WallpaperManager
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.ibaevzz.sportsquiz.databinding.ActivityWallpaperBinding
import com.ibaevzz.sportsquiz.databinding.WallpaperBinding

class WallpaperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperBinding
    private lateinit var pref: SharedPreferences
    private lateinit var set: Set<String>
    private lateinit var manager: WallpaperManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("main_pref", MODE_PRIVATE)
        binding.score.text = pref.getInt("score", 0).toString()

        set = pref.getStringSet("isOpen", emptySet()) as Set<String>

        val list = listOf(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d)
        binding.wallpapers.adapter = Adapter(list)

        manager = WallpaperManager.getInstance(applicationContext)
    }

    class PagerVH(val binding: WallpaperBinding): RecyclerView.ViewHolder(binding.root)

    inner class Adapter(val list: List<Int>): RecyclerView.Adapter<PagerVH>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
            return PagerVH(WallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: PagerVH, position: Int) {
            holder.binding.wallpaper.setImageResource(list[position])

            val builder = AlertDialog.Builder(this@WallpaperActivity)
            builder.setTitle("Установка обоев")
                .setMessage("Вы хотите установить обои на рабочий стол?")
                .setPositiveButton("Да") {dialog, _ ->
                    Thread{
                        manager.setBitmap(BitmapFactory.decodeResource(resources, list[position]))
                    }.start()
                    dialog.cancel()
                }.setNegativeButton("Нет"){
                        dialog, _ ->  dialog.cancel()
                }

            if(list[position].toString() in set){
                holder.binding.greyBack.visibility = View.GONE
                holder.binding.lock.visibility = View.GONE
                holder.binding.root.setOnClickListener{
                    builder.create().show()
                }
            }else{
                holder.binding.root.setOnClickListener{
                    val builderBuy = AlertDialog.Builder(this@WallpaperActivity)
                    builderBuy.setTitle("Покупка обоев")
                        .setMessage("Вы хотите купить эти обои за 10 очков?")
                        .setPositiveButton("Да") { dialog, _ ->
                            if(pref.getInt("score", 0)>=10) {
                                set = set + list[position].toString()
                                Toast.makeText(
                                    this@WallpaperActivity,
                                    set.size.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                pref.edit().putStringSet("isOpen", set)
                                    .putInt("score", pref.getInt("score", 0)-10)
                                    .apply()
                                holder.binding.greyBack.visibility = View.GONE
                                holder.binding.lock.visibility = View.GONE
                                holder.binding.root.setOnClickListener{
                                    builder.create().show()
                                }
                            }else{
                                Toast.makeText(this@WallpaperActivity, "Недостаточно очков", Toast.LENGTH_SHORT).show()
                            }
                            dialog.cancel()
                        }.setNegativeButton("Нет"){
                                dialog, _ ->  dialog.cancel()
                        }
                    builderBuy.create().show()
                }
            }
        }
    }
}