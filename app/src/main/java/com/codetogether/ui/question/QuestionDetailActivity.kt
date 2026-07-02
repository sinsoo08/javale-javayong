package com.codetogether.ui.question

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetogether.data.repository.SampleDataRepository
import com.codetogether.databinding.ActivityQuestionDetailBinding

class QuestionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionDetailBinding
    private var postId: Int = -1

    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        postId = intent.getIntExtra(EXTRA_POST_ID, -1)
        loadPost()

        // ★ 댓글 등록 버튼 - 실제 저장
        binding.btnSubmitComment.setOnClickListener {
            val commentText = binding.etComment.text.toString().trim()
            if (commentText.isBlank()) {
                Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            SampleDataRepository.addComment(postId, commentText)
            binding.etComment.setText("")
            loadComments() // 댓글 목록 새로고침
            Toast.makeText(this, "답변이 등록되었습니다! ✅", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadPost() {
        val post = SampleDataRepository.getPostById(postId) ?: run {
            finish()
            return
        }

        supportActionBar?.title = post.category.displayName

        binding.tvTitle.text = post.title
        binding.tvContent.text = post.content
        binding.tvAuthor.text = post.author
        binding.tvAuthorLevel.text = "[${post.authorLevel.displayName}]"
        binding.tvDate.text = post.getFormattedDate()
        binding.tvLikeCount.text = "👍 ${post.likeCount}"
        binding.tvViewCount.text = "👁 ${post.viewCount}"
        binding.tvLanguageTag.text = post.language.displayName
        binding.tvLanguageTag.setBackgroundColor(Color.parseColor(post.language.colorHex))

        if (post.tags.isNotEmpty()) {
            binding.tvTags.text = post.tags.joinToString(" ") { "#$it" }
            binding.tvTags.visibility = View.VISIBLE
        }

        if (post.codeSnippet != null) {
            binding.cardCode.visibility = View.VISIBLE
            binding.tvCodeSnippet.text = post.codeSnippet
        } else {
            binding.cardCode.visibility = View.GONE
        }

        loadComments()
    }

    private fun loadComments() {
        val comments = SampleDataRepository.getCommentsByPostId(postId)
        binding.tvCommentCount.text = "답변 ${comments.size}개"
        val adapter = CommentAdapter(comments)
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(this@QuestionDetailActivity)
            this.adapter = adapter
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
