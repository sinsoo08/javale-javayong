package com.codetogether.ui.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codetogether.R
import com.codetogether.data.model.Comment
import com.codetogether.databinding.ItemCommentBinding

class CommentAdapter(
    private val comments: List<Comment>
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment) {
            binding.tvAuthor.text = comment.author
            binding.tvAuthorLevel.text = "[${comment.authorLevel.displayName}]"
            binding.tvContent.text = comment.content
            binding.tvDate.text = comment.getFormattedDate()
            binding.tvLikeCount.text = "👍 ${comment.likeCount}"

            if (comment.isAccepted) {
                binding.tvAccepted.visibility = View.VISIBLE
                binding.root.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.accepted_bg)
                )
            } else {
                binding.tvAccepted.visibility = View.GONE
                binding.root.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, android.R.color.white)
                )
            }

            if (comment.codeSnippet != null) {
                binding.cardCode.visibility = View.VISIBLE
                binding.tvCodeSnippet.text = comment.codeSnippet
            } else {
                binding.cardCode.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount() = comments.size
}
