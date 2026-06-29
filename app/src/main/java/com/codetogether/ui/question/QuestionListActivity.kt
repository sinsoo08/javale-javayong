package com.codetogether.ui.question

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetogether.data.model.Post
import com.codetogether.data.model.PostCategory
import com.codetogether.data.model.ProgrammingLanguage
import com.codetogether.data.repository.SampleDataRepository
import com.codetogether.databinding.ActivityQuestionListBinding

class QuestionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionListBinding

    companion object {
        const val EXTRA_LANGUAGE = "extra_language"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val languageName = intent.getStringExtra(EXTRA_LANGUAGE)
        val language = languageName?.let { ProgrammingLanguage.valueOf(it) }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = language?.displayName ?: "전체 게시글"
        }

        setupFilterChips(language)
        loadPosts(language, null)

        binding.fabAskQuestion.setOnClickListener {
            val intent = Intent(this, AskQuestionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupFilterChips(language: ProgrammingLanguage?) {
        binding.chipAll.setOnClickListener { loadPosts(language, null) }
        binding.chipQuestion.setOnClickListener { loadPosts(language, PostCategory.QUESTION) }
        binding.chipDiscussion.setOnClickListener { loadPosts(language, PostCategory.DISCUSSION) }
        binding.chipSharing.setOnClickListener { loadPosts(language, PostCategory.SHARING) }
        binding.chipReview.setOnClickListener { loadPosts(language, PostCategory.REVIEW) }
        binding.chipRecruit.setOnClickListener { loadPosts(language, PostCategory.RECRUIT) }
    }

    private fun loadPosts(language: ProgrammingLanguage?, category: PostCategory?) {
        val posts = SampleDataRepository.getPosts(language, category)
        val adapter = PostAdapter(posts) { post -> openPostDetail(post) }
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(this@QuestionListActivity)
            this.adapter = adapter
        }
        binding.tvPostCount.text = "총 ${posts.size}개 게시글"
    }

    private fun openPostDetail(post: Post) {
        val intent = Intent(this, QuestionDetailActivity::class.java)
        intent.putExtra(QuestionDetailActivity.EXTRA_POST_ID, post.id)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
