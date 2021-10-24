package com.example.flo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.FragmentHomeBinding
import org.w3c.dom.Text

class AlbumRecyclerViewAdapter(private val albumList: ArrayList<Album>) :
        RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder>(){

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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title: TextView = view.findViewById(R.id.item_today_music_album_title_tv)
        var singer: TextView = view.findViewById(R.id.item_today_music_album_singer_tv)
        var cover: ImageView = view.findViewById(R.id.item_today_music_album_img_iv)
        var playBtn: ImageView = view.findViewById(R.id.item_today_music_album_img_play_iv)
    }

}