package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerSavedsongBinding

class SavedSongFragment : Fragment() {

    lateinit var binding : FragmentLockerSavedsongBinding
    private var songDataList = ArrayList<Song>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)

        // songDatas에 더미데이터
        inputDummyData()

        val songRecyclerViewAdapter = SongRVAdapter(songDataList)
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //리스너 객체 생성 및 전달
        songRecyclerViewAdapter.setMyItemClickListener(object : SongRVAdapter.MyItemClickListener{
            override fun onRemoveSong(position: Int) {
                songRecyclerViewAdapter.removeItem(position)
            }
        })
        binding.lockerSavedSongRecyclerView.adapter = songRecyclerViewAdapter

        return binding.root
    }

    private fun inputDummyData() {
        songDataList.apply {
            add(Song("Lilac", "아이유 (IU)", 0, 0, false, "", R.drawable.img_album_exp2))
            add(Song("Butter", "방탄소년단 (BTS)", 0, 0, false, "", R.drawable.img_album_exp))
            add(Song("Next Level", "에스파 (AESPA)", 0, 0, false, "", R.drawable.img_album_exp3))
            add(Song("Boy with Luv", "방탄소년단 (BTS)", 0, 0, false, "", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드 (MOMOLAND)", 0, 0, false, "", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연 (Tae Yeon)", 0, 0, false, "", R.drawable.img_album_exp6))
            add(Song("Lilac", "아이유 (IU)", 0, 0, false, "", R.drawable.img_album_exp2))
            add(Song("Butter", "방탄소년단 (BTS)", 0, 0, false, "", R.drawable.img_album_exp))
            add(Song("Next Level", "에스파 (AESPA)", 0, 0, false, "", R.drawable.img_album_exp3))
            add(Song("Boy with Luv", "방탄소년단 (BTS)", 0, 0, false, "", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드 (MOMOLAND)", 0, 0, false, "", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연 (Tae Yeon)", 0, 0, false, "", R.drawable.img_album_exp6))
            add(Song("Lilac", "아이유 (IU)", 0, 0, false, "", R.drawable.img_album_exp2))
            add(Song("Butter", "방탄소년단 (BTS)", 0, 0, false, "", R.drawable.img_album_exp))
            add(Song("Next Level", "에스파 (AESPA)", 0, 0, false, "", R.drawable.img_album_exp3))
            add(Song("Boy with Luv", "방탄소년단 (BTS)", 0, 0, false, "", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드 (MOMOLAND)", 0, 0, false, "", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연 (Tae Yeon)", 0, 0, false, "", R.drawable.img_album_exp6))
        }
    }
}