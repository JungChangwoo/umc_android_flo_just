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
            mediaPlayer?.start()
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
            timer.isPlaying = false
            mediaPlayer?.pause()
        }
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("playTime") && intent.hasExtra("isPlaying") && intent.hasExtra("music")){
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.playTime = intent.getIntExtra("playTime", 0)
            song.isPlaying = intent.getBooleanExtra("isPlaying", false)
            song.music = intent.getStringExtra("music")!!
            val music = resources.getIdentifier(song.music, "raw", this.packageName)

            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
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
    override fun onPause() {
        super.onPause()
        timer.isPlaying = false; // 스레드 멈춤
        setPlayerStatus(false); // 멈춤상태로 이미지 전환

        val second = (song.playTime*binding.musicplayerProgressSb.progress)/1000
        val saveSong = Song(song.title, song.singer, second, song.playTime, false)

        val sharedPreferences = getSharedPreferences("saveSong", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit() //sharedpreference를 쓰고 지우기와 같은 조작을 하려면 editor를 사용해야 한다.
        editor.putString("title", saveSong.title)
        editor.putString("singer", saveSong.singer)
        editor.putInt("second", saveSong.second)
        editor.putInt("playTime", saveSong.playTime)
        editor.putBoolean("isPlaying", saveSong.isPlaying)

        editor.commit()
    }
    // 데이터 저장
    override fun onStop() {
        super.onStop()
        
        mediaPlayer?.pause()
    }
    // 액티비티가 종료될 때
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt() // 스레드를 해제함
        mediaPlayer?.release() // 미디어플레이어가 가지고 있던 리소스를 해방
        mediaPlayer = null // 앱이 종료될 때 미디어플레이어 자원을 해제시켜줘야 한다.
    }
}