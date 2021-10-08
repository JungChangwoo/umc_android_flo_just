package com.example.flo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    //소괄호 : 클래스를 다른 클래스로 상속을 진행할 때는 소괄호를 넣어줘야 한다.

    //전역 변수
    lateinit var binding : ActivitySongBinding

    private var isRunning = true

    lateinit var timer: Timer

    private var reapeatIdx=0

    private var minute = 0
    private var second = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
        timer = Timer()

        timer.start()
        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

    }

    fun setPlayerStatus (isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        } else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
    inner class Timer : Thread() {

        @SuppressLint("SetTextI18n")
        override fun run() {
            while(true) {
                try {
                    sleep(1000)
                } catch (e: InterruptedException) {
                }
                if (isRunning && minute<1) {

                    second += 1F

                    if (second >= 60) {
                        second = 0F
                        minute += 1
                    }

                    if (reapeatIdx == 2 && minute == 1){
                        minute = 0
                        second = 0F
                    }

                    runOnUiThread {
                        if (minute < 10 && second < 10) {
                            binding.songStartTimeTv.text = "0${minute}:0${second.toInt()}"
                        } else if(minute < 10 && second>=10){
                            binding.songStartTimeTv.text = "0${minute}:${second.toInt()}"
                        }

                        binding.musicplayerProgressBarSb.progress = ((second+minute*60) / 60F * 100).toInt()
                    }

                }

            }


        }
    }
}