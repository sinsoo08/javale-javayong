package com.codetogether.ui.question

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetogether.data.repository.SampleDataRepository
import com.codetogether.databinding.ActivityQuestionDetailBinding

class QuestionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionDetailBinding

    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val postId = intent.getIntExtra(EXTRA_POST_ID, -1)
        val post = SampleDataRepository.getPostById(postId) ?: run {
            finish()
            return
        }

        supportActionBar?.title = post.category.displayName

        // Post details
        binding.tvTitle.text = post.title
        binding.tvContent.text = post.content
        binding.tvAuthor.text = post.author
        binding.tvAuthorLevel.text = "[${post.authorLevel.displayName}]"
        binding.tvDate.text = post.getFormattedDate()
        binding.tvLikeCount.text = "👍 ${post.likeCount}"
        binding.tvViewCount.text = "👁 ${post.viewCount}"
        binding.tvLanguageTag.text = post.language.displayName
        binding.tvLanguageTag.setBackgroundColor(Color.parseColor(post.language.colorHex))

        // Tags
        if (post.tags.isNotEmpty()) {
            binding.tvTags.text = post.tags.joinToString(" ") { "#$it" }
            binding.tvTags.visibility = View.VISIBLE
        }

        // Code snippet
        if (post.codeSnippet != null) {
            binding.cardCode.visibility = View.VISIBLE
            binding.tvCodeSnippet.text = post.codeSnippet
        } else {
            binding.cardCode.visibility = View.GONE
        }

        // Comments
        val comments = SampleDataRepository.getCommentsByPostId(postId)
        binding.tvCommentCount.text = "답변 ${comments.size}개"

        val adapter = CommentAdapter(comments)
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(this@QuestionDetailActivity)
            this.adapter = adapter
        }

        binding.btnSubmitComment.setOnClickListener {
            val commentText = binding.etComment.text.toString()
            if (commentText.isNotBlank()) {
                binding.etComment.setText("")
                // In a real app, this would save to a backend
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
