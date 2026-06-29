package com.codetogether.ui.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetogether.data.model.CommunityChannel
import com.codetogether.data.repository.SampleDataRepository
import com.codetogether.databinding.FragmentCommunityBinding
import com.codetogether.ui.question.QuestionListActivity

class CommunityFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChannelList()
    }

    private fun setupChannelList() {
        val channels = SampleDataRepository.getCommunityChannels()
        val adapter = ChannelAdapter(channels) { channel ->
            val intent = Intent(requireContext(), QuestionListActivity::class.java)
            intent.putExtra(QuestionListActivity.EXTRA_LANGUAGE, channel.language.name)
            startActivity(intent)
        }
        binding.rvChannels.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
