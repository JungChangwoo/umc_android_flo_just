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
//      val song = Song("라일락", "아이유(IU)", 0,215, false, "music_lilac")
//      setMiniPlayer(song)

        Log.d("LOG test",binding.mainMiniplayerTitleTv.text.toString()+ binding.mainMiniplayerSingerTv.text.toString())

        binding.mainPlayerLayout.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)

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
    public fun set(song : Song){

    }


    private fun setMiniPlayer(song : Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if(song.isPlaying) {
            binding.mainMiniplayerPauseIv.visibility = View.VISIBLE
            binding.mainMiniplayerPlayIv.visibility = View.GONE
        } else {
            binding.mainMiniplayerPauseIv.visibility = View.GONE
            binding.mainMiniplayerPlayIv.visibility = View.VISIBLE
        }
    }
    //onCreate가 아니라 onStart에서 해주는 이유는 Main입장에서 Song으로 갔다가 다시 돌아왔을 때에도 Song에 있던
    //데이터들을 miniPlayer에 반영해주어야 하잖아요? 그래서 onStart
    override fun onStart() {
        super.onStart()

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songData = sharedPreferences.getString("song", null)
        song = if(songData == null){
            Song("라락","아이유(IU)", 0,215, false, "music_lilac")
        } else {
            gson.fromJson(songData, Song::class.java)
        }
        setMiniPlayer(song)
    }
}

