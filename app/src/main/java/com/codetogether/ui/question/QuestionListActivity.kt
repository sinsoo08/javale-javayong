package com.codetogether.ui.question

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetogether.data.model.Post
import com.codetogether.data.model.PostCategory
import com.codetogether.data.model.ProgrammingLanguage
import com.codetogether.data.repository.SampleDataRepository
import com.codetogether.databinding.ActivityQuestionListBinding

class QuestionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionListBinding
    private var currentLanguage: ProgrammingLanguage? = null
    private var currentCategory: PostCategory? = null

    companion object {
        const val EXTRA_LANGUAGE = "extra_language"
    }

    // 글 작성 후 돌아오면 자동 새로고침
    private val askLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            loadPosts(currentLanguage, currentCategory) // 새로고침!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val languageName = intent.getStringExtra(EXTRA_LANGUAGE)
        currentLanguage = languageName?.let { ProgrammingLanguage.valueOf(it) }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = currentLanguage?.displayName ?: "전체 게시글"
        }

        setupFilterChips()
        loadPosts(currentLanguage, null)

        binding.fabAskQuestion.setOnClickListener {
            val intent = Intent(this, AskQuestionActivity::class.java)
            askLauncher.launch(intent) // 결과를 받는 방식으로 실행
        }
    }

    private fun setupFilterChips() {
        binding.chipAll.setOnClickListener {
            currentCategory = null
            loadPosts(currentLanguage, null)
        }
        binding.chipQuestion.setOnClickListener {
            currentCategory = PostCategory.QUESTION
            loadPosts(currentLanguage, PostCategory.QUESTION)
        }
        binding.chipDiscussion.setOnClickListener {
            currentCategory = PostCategory.DISCUSSION
            loadPosts(currentLanguage, PostCategory.DISCUSSION)
        }
        binding.chipSharing.setOnClickListener {
            currentCategory = PostCategory.SHARING
            loadPosts(currentLanguage, PostCategory.SHARING)
        }
        binding.chipReview.setOnClickListener {
            currentCategory = PostCategory.REVIEW
            loadPosts(currentLanguage, PostCategory.REVIEW)
        }
        binding.chipRecruit.setOnClickListener {
            currentCategory = PostCategory.RECRUIT
            loadPosts(currentLanguage, PostCategory.RECRUIT)
        }
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
