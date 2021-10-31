package com.example.flo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson
import org.w3c.dom.Text

class AlbumRecyclerViewAdapter(private val albumList: ArrayList<Album>) :
        RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder>(){

    var album: Album = Album()
    val gson: Gson = Gson()

    // 클릭 인터페이스를 정의
    interface MyItemClickListener{
        fun onItemClick(position: Int)
    }
    // 클릭 리스너 선언
    private lateinit var mItemClickListener: MyItemClickListener

    // 클릭 리스너 등록 메서드 (메인 액티비티에서 inner Class로 호출)
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_album, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = albumList[position].title
        holder.singer.text = albumList[position].singer
        holder.cover.setImageResource(albumList[position].cover!!)
    }

    override fun getItemCount() = albumList.size

    //아이템 뷰에서 클릭 이벤트를 처리하고, 아이템 뷰는 뷰홀더 객체가 가지고 있다.
    // => 아이템 클릭 이벤트는 뷰홀더에서 처리
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title: TextView = view.findViewById(R.id.item_today_music_album_title_tv)
        var singer: TextView = view.findViewById(R.id.item_today_music_album_singer_tv)
        var cover: ImageView = view.findViewById(R.id.item_today_music_album_img_iv)
        var playBtn: ImageView = view.findViewById(R.id.item_today_music_album_img_play_iv)
        //초기화
        init {
            view.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }
    }
}