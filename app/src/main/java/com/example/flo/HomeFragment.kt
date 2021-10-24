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
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp))
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp2))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp))
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp2))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp))
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp2))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp))
        }
        //더미데이터와 Adapter 연결
        val albumRecyclerViewAdapter = AlbumRecyclerViewAdapter(albumDatas as ArrayList<Album>)
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