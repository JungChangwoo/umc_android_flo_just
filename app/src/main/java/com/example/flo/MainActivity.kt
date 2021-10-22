package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var song: Song
    // 미디어 플레이어
    private var mediaPlayer: MediaPlayer? = null
    // Gson
    private val gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

//        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString())
//        val song = Song("라일락", "아이유(IU)", 215, false)
//        setMiniPlayer(song)

        Log.d("LOG test",binding.mainMiniplayerTitleTv.text.toString()+ binding.mainMiniplayerSingerTv.text.toString())

        binding.mainPlayerLayout.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)
            val song = Song("라일락락락", "아이유(IU) 아이유", 0,215, false, "music_lilac")
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music",song.music)

            startActivity(intent)
        }

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
    }


    private fun setMiniPlayer(song : Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second * 1000 / song.playTime)
//        song.music = intent.getStringExtra("music")!!
//        val music = resources.getIdentifier(song.music, "raw", this.packageName)
//        mediaPlayer = MediaPlayer.create(this, music)

        if(song.isPlaying) {
            binding.mainMiniplayerPauseIv.visibility = View.VISIBLE
            binding.mainMiniplayerPlayIv.visibility = View.GONE
        } else {
            binding.mainMiniplayerPauseIv.visibility = View.GONE
            binding.mainMiniplayerPlayIv.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songData = sharedPreferences.getString("songData", null)
        if(songData == null){
            song = Song("제목", "가수", 0,100, false, null)
        } else {
            song = gson.fromJson(songData, Song::class.java)
        }
        setMiniPlayer(song)
    }
}

