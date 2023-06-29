package com.example.mybottomnavapp.ui.character

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mybottomnavapp.R
import com.example.mybottomnavapp.databinding.ItemEpisodeBinding
import android.content.Context;


class EpisodesAdapter(
    val episode: List<String>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {


    class ViewHolder(val view: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        val binding = ItemEpisodeBinding.bind(view)

        fun updateUI(s: String) {
            binding.apply {
                tvLink.text = s
            }
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return ViewHolder(
            view, onItemClicked
        )
    }

    override fun getItemCount() = episode.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.updateUI(episode[position])

    }


}