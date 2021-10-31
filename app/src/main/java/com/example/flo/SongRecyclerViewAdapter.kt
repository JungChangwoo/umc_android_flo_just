package com.example.flo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongRecyclerViewAdapter(private val songList: ArrayList<Song>) :
        RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SongRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_song, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.title.text = songList[position].title
        holder.singer.text = songList[position].singer
        holder.cover.setImageResource(songList[position].cover!!)
    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title: TextView = view.findViewById(R.id.item_song_title_tv)
        var singer: TextView = view.findViewById(R.id.item_song_singer_tv)
        var cover: ImageView = view.findViewById(R.id.item_song_img_iv)
        var play: ImageView = view.findViewById(R.id.item_song_play_iv)
        var more: ImageView = view.findViewById(R.id.item_song_more_iv)
    }

}