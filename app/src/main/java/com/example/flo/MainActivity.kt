package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

//        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString())
//        val song = Song("라일락", "아이유(IU)", 215, false) >> setOnClick으로
//        setMiniPlayer(song)

        Log.d("LOG test",binding.mainMiniplayerTitleTv.text.toString()+ binding.mainMiniplayerSingerTv.text.toString())

        binding.mainPlayerLayout.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)
            val song = Song("라일락락", "아이유(IU)", 0,215, false)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
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

        val sharedPreferences = getSharedPreferences("saveSong", MODE_PRIVATE)
        val title = sharedPreferences.getString("title", "제목")
        val singer = sharedPreferences.getString("singer", "가수")
        val second = sharedPreferences.getInt("second", 0)
        val playTime = sharedPreferences.getInt("playTime", 0);
        val isPlaying = sharedPreferences.getBoolean("isPlaying", false)
        val song = Song(title, singer, second, playTime, isPlaying) //Song dataClass 에 ?추가
        setMiniPlayer(song)
    }
}

