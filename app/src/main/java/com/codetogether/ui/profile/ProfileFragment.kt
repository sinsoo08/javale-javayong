package com.codetogether.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codetogether.data.repository.SampleDataRepository
import com.codetogether.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = SampleDataRepository.getCurrentUser()

        binding.tvNickname.text = user.nickname
        binding.tvLevel.text = user.level.displayName
        binding.tvBio.text = user.bio
        binding.tvPostCount.text = "${user.postCount}"
        binding.tvAnswerCount.text = "${user.answerCount}"
        binding.tvLikeCount.text = "${user.likeReceived}"
        binding.tvEmail.text = user.email

        val favLanguages = user.favoriteLanguages.joinToString(", ") { it.displayName }
        binding.tvFavoriteLanguages.text = if (favLanguages.isNotEmpty()) favLanguages else "설정 안 됨"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
