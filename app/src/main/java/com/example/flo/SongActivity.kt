package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            playSong(true)
        }

        binding.songPauseIv.setOnClickListener {
            playSong(false)
        }
    }


   private fun playSong(isPlaying: Boolean) {
       if(isPlaying) {
           binding.songMiniplayerIv.visibility = View.GONE
           binding.songPauseIv.visibility = View.VISIBLE
       } else {
           binding.songPauseIv.visibility = View.GONE
           binding.songMiniplayerIv.visibility = View.VISIBLE
       }
   }
}