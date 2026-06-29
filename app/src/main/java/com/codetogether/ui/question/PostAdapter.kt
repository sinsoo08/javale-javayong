package com.codetogether.ui.question

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetogether.data.model.Post
import com.codetogether.databinding.ItemPostBinding

class PostAdapter(
    private val posts: List<Post>,
    private val onItemClick: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.tvTitle.text = post.title
            binding.tvAuthor.text = post.author
            binding.tvDate.text = post.getFormattedDate()
            binding.tvLikeCount.text = "👍 ${post.likeCount}"
            binding.tvCommentCount.text = "💬 ${post.commentCount}"
            binding.tvViewCount.text = "👁 ${post.viewCount}"
            binding.tvCategory.text = post.category.displayName
            binding.tvLanguageTag.text = post.language.displayName
            binding.tvLanguageTag.setBackgroundColor(
                Color.parseColor(post.language.colorHex)
            )
            binding.tvAuthorLevel.text = post.authorLevel.displayName

            // Show code snippet indicator if available
            if (post.codeSnippet != null) {
                binding.tvCodeIndicator.visibility = android.view.View.VISIBLE
            } else {
                binding.tvCodeIndicator.visibility = android.view.View.GONE
            }

            binding.root.setOnClickListener { onItemClick(post) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size
}
