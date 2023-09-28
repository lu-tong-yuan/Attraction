package com.tommy.attractions.features.detail.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tommy.attractions.MainActivity
import com.tommy.attractions.R
import com.tommy.attractions.WebViewActivity
import com.tommy.attractions.databinding.FragmentDetailBinding
import com.tommy.attractions.features.detail.adapter.MyPagerAdapter
import com.tommy.attractions.features.main.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding:FragmentDetailBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val TAG = "TAG_DetailFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.toolbarMain
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)

        binding.toolbarMain.setNavigationIcon(R.drawable.arrow_left)
        binding.toolbarMain.setNavigationOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.tvUrl.paint.flags = Paint.UNDERLINE_TEXT_FLAG;

        viewModel.attraction.observe(viewLifecycleOwner) {
            binding.toolbarMain.title = it.name
            binding.tvName.text = it.name
            binding.tvIntroduction.text = it.introduction
            binding.tvAddress.text = "Address\n${it.address}"
            binding.tvTime.text = "Last Updated Time\n${it.modified}"
            binding.tvUrl.text = it.url
            binding.vpPhoto.adapter = MyPagerAdapter(it.images)
        }

        binding.tvUrl.setOnClickListener {
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            intent.putExtra("url", binding.tvUrl.text.toString())
            startActivity(intent)
        }
    }
}