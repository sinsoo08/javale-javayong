package com.codetogether.ui.question

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codetogether.data.model.PostCategory
import com.codetogether.data.model.ProgrammingLanguage
import com.codetogether.databinding.ActivityAskQuestionBinding

class AskQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAskQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "새 게시글 작성"
        }

        setupSpinners()

        binding.btnSubmit.setOnClickListener {
            submitPost()
        }
    }

    private fun setupSpinners() {
        // Language spinner
        val languages = ProgrammingLanguage.values().map { it.displayName }
        val langAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguage.adapter = langAdapter

        // Category spinner
        val categories = PostCategory.values().map { it.displayName }
        val catAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = catAdapter
    }

    private fun submitPost() {
        val title = binding.etTitle.text.toString().trim()
        val content = binding.etContent.text.toString().trim()

        if (title.isEmpty()) {
            binding.etTitle.error = "제목을 입력해주세요"
            return
        }
        if (content.isEmpty()) {
            binding.etContent.error = "내용을 입력해주세요"
            return
        }

        // In a real app, this would call an API
        Toast.makeText(this, "게시글이 등록되었습니다!", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
