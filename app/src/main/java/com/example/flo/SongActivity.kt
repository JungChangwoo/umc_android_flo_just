package com.example.flo

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson
import java.util.concurrent.TimeUnit

class SongActivity : AppCompatActivity() {
    //소괄호 : 클래스를 다른 클래스로 상속을 진행할 때는 소괄호를 넣어줘야 한다.
    //전역 변수
    lateinit var binding: ActivitySongBinding
    lateinit var timer: Timer
    //    private var handler=Handler(Looper.getMainLooper())
    private val song: Song = Song()
    // 미디어 플레이어
    private var mediaPlayer: MediaPlayer? = null
    // Gson
    private val gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()

        timer = Timer(song.playTime, song.isPlaying)
        timer.start()

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
            timer.isPlaying = true
            song.isPlaying = true
            mediaPlayer?.start()
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
            timer.isPlaying = false
            song.isPlaying = false
            mediaPlayer?.pause()
        }
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("second") && intent.hasExtra("playTime") && intent.hasExtra("isPlaying") && intent.hasExtra("music")){
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.second = intent.getIntExtra("second", 0)
            song.playTime = intent.getIntExtra("playTime", 0)
            song.isPlaying = intent.getBooleanExtra("isPlaying", false)
            song.music = intent.getStringExtra("music")!!
            val music = resources.getIdentifier(song.music, "raw", this.packageName)

            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
            binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
            binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
            setPlayerStatus(song.isPlaying)
            mediaPlayer = MediaPlayer.create(this, music)
        }
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    inner class Timer(private val playTime: Int = 0, var isPlaying: Boolean = false) : Thread() {
        private var second : Long = 0

        @SuppressLint("SetTextI18n")
        override fun run() {
            try {
                while(true) {
                    if(second >= playTime){
                        break
                    }

                    if (isPlaying) {
                        sleep(1000)
                        second++

                        runOnUiThread {
                            binding.musicplayerProgressSb.progress = (second * 1000 / playTime).toInt()
                            binding.songStartTimeTv.text = String.format("%02d:%02d", TimeUnit.SECONDS.toMinutes(second), second % 60)
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("SONG", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }

    // onStop 에서 하면 main 이랑 바로 동기화가 잘 안 됨
    // 데이터 저장  // 포커스를 잃었을 때, 음악을 중지한다고 가정
    override fun onPause() {
        super.onPause()
        // pause 될 때 현재 상태를 song data 객체에 업데이트 해줘야 겠죠?
        song.isPlaying = false
        song.second = (song.playTime*binding.musicplayerProgressSb.progress)/1000
        timer.isPlaying = false; // 스레드 멈춤
        mediaPlayer?.pause()
        setPlayerStatus(false); // 멈춤상태로 이미지 전환
        // sharedPreferences 에 저장
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = gson.toJson(song) //song 객체를 Json으로
        editor.putString("song", json) //Json 형으로 저장

        editor.apply()
    }

    // 앱이 종료될 때 리소스 해제
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt() // 스레드를 해제함
        mediaPlayer?.release() // 미디어플레이어가 가지고 있던 리소스를 해방
        mediaPlayer = null // 미디어플레이어 해제
    }
}