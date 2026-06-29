package com.codetogether.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetogether.data.model.Post
import com.codetogether.data.repository.SampleDataRepository
import com.codetogether.databinding.FragmentHomeBinding
import com.codetogether.ui.question.PostAdapter
import com.codetogether.ui.question.QuestionDetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPopularPosts()
        setupRecentPosts()
        setupStats()
    }

    private fun setupPopularPosts() {
        val popularPosts = SampleDataRepository.getPopularPosts()
        val adapter = PostAdapter(popularPosts) { post -> openPostDetail(post) }
        binding.rvPopularPosts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
    }

    private fun setupRecentPosts() {
        val recentPosts = SampleDataRepository.getRecentPosts()
        val adapter = PostAdapter(recentPosts) { post -> openPostDetail(post) }
        binding.rvRecentPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun setupStats() {
        val totalPosts = SampleDataRepository.getPosts().size
        val totalMembers = SampleDataRepository.getCommunityChannels().sumOf { it.memberCount }
        binding.tvTotalPosts.text = "${totalPosts}개"
        binding.tvTotalMembers.text = "${totalMembers}명"
    }

    private fun openPostDetail(post: Post) {
        val intent = Intent(requireContext(), QuestionDetailActivity::class.java)
        intent.putExtra(QuestionDetailActivity.EXTRA_POST_ID, post.id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
