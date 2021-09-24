package com.example.flo

import android.content.res.Resources
import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding

    val Int.dpToFloat: Float
        get() = (this * Resources.getSystem().displayMetrics.density).toFloat()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songMiniplayerIv.setOnClickListener {

        }
        binding.songMiniplayerIv.setOnClickListener {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseBtn.visibility = View.VISIBLE
        }

        binding.songPauseBtn.setOnClickListener {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseBtn.visibility = View.GONE
        }
        binding.songDownIb.setOnClickListener {
            finish()
        }



        roundAll(binding.songAlbumIv , 10)
    }

    fun roundAll(iv: ImageView, curveRadius : Int)  : ImageView {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            iv.outlineProvider = object : ViewOutlineProvider() {

                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, view!!.width, view.height, curveRadius.dpToFloat)
                }
            }

            iv.clipToOutline = true
        }
        return iv
    }
}