package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerSavedsongBinding
import com.example.flo.databinding.FragmentVideoBinding

class SavedSongFragment : Fragment() {

    lateinit var binding : FragmentLockerSavedsongBinding
    private var songDatas = mutableListOf<Song>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)

        // recyclerView
        // 더미데이터 생성
        songDatas.apply {
            add(Song("Lilac", "아이유 (IU)", 0, 0, false, "", R.drawable.img_album_exp2))
            add(Song("Butter", "방탄소년단 (BTS)", 0, 0, false, "", R.drawable.img_album_exp))
            add(Song("Next Level", "에스파 (AESPA)", 0, 0, false, "", R.drawable.img_album_exp3))
            add(Song("Boy with Luv", "방탄소년단 (BTS)", 0, 0, false, "", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드 (MOMOLAND)", 0, 0, false, "", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연 (Tae Yeon)", 0, 0, false, "", R.drawable.img_album_exp6))
        }
        //더미데이터와 Adapter 연결
        val songRecyclerViewAdapter = SongRecyclerViewAdapter(songDatas as ArrayList<Song>)
        binding.lockerSavedSongRecyclerView.adapter = songRecyclerViewAdapter

        return binding.root
    }
}