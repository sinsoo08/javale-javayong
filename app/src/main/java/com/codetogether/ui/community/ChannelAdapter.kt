package com.codetogether.ui.community

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetogether.data.model.CommunityChannel
import com.codetogether.databinding.ItemChannelBinding

class ChannelAdapter(
    private val channels: List<CommunityChannel>,
    private val onItemClick: (CommunityChannel) -> Unit
) : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: CommunityChannel) {
            binding.tvLanguageName.text = channel.language.displayName
            binding.tvDescription.text = channel.description
            binding.tvMemberCount.text = "${channel.memberCount}명"
            binding.tvPostCount.text = "${channel.postCount}개 게시글"
            binding.viewColorIndicator.setBackgroundColor(
                Color.parseColor(channel.language.colorHex)
            )
            binding.root.setOnClickListener { onItemClick(channel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChannelBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(channels[position])
    }

    override fun getItemCount() = channels.size
}
