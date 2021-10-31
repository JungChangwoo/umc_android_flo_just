package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var albumDatas = mutableListOf<Album>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeTodayMusicAlbumImg01Iv.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                    .replace(R.id.main_frm, AlbumFragment())
//                    .commitAllowingStateLoss()
//        }
        // recyclerView
        // 더미데이터 생성
        albumDatas.apply {
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }
        //더미데이터와 Adapter 연결
        val albumRecyclerViewAdapter = AlbumRecyclerViewAdapter(albumDatas as ArrayList<Album>)
        //리스너 객체 생성 및 전달
        albumRecyclerViewAdapter.setMyItemClickListener(object : AlbumRecyclerViewAdapter.MyItemClickListener{
            override fun onItemClick(position: Int) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, AlbumFragment())
                        .commitAllowingStateLoss()
            }
        })
        binding.homeTodayMusicAlbumRecyclerView.adapter = albumRecyclerViewAdapter

        val bannerAdapter = HomeViewpagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        //bannerAdapter.fragmentlist.add((BannerFragment(R.drawable.img_home_viewpager_exp)))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root


    }


}