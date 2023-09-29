package com.tommy.attractions.features.main.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tommy.attractions.MainActivity
import com.tommy.attractions.R
import com.tommy.attractions.databinding.FragmentMainBinding
import com.tommy.attractions.features.main.OnItemClickListener
import com.tommy.attractions.features.main.adapter.AttractionAdapter
import com.tommy.attractions.features.main.adapter.LanguageAdapter
import com.tommy.attractions.features.main.model.Attraction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val TAG = "TAG_MainFragment"
    private lateinit var languageDialog: AlertDialog
    private lateinit var adapter: AttractionAdapter
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lanDialog = AlertDialog.Builder(requireContext())
        val vw = layoutInflater.inflate(R.layout.language_dialog_layout, null)
        lanDialog.setView(vw)
        val languageRecycler = vw.findViewById<RecyclerView>(R.id.recycler)
        languageDialog = lanDialog.create()
        languageRecycler.layoutManager = LinearLayoutManager(requireContext())
        languageRecycler.setHasFixedSize(true)
        languageRecycler.adapter = LanguageAdapter(viewModel)


        val toolbar = binding.toolbarMain
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_language -> {
                        languageDialog.show()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        adapter = AttractionAdapter(object : OnItemClickListener {
            override fun onItemClick(attraction: Attraction) {
                viewModel.updateAttraction(attraction)
                NavHostFragment.findNavController(this@MainFragment)
                    .navigate(R.id.detailFragment)
            }
        },viewModel.getAttractions())
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = (binding.recycler.layoutManager as LinearLayoutManager).childCount
                val totalItemCount = (binding.recycler.layoutManager as LinearLayoutManager).itemCount
                val firstVisibleItemPosition = (binding.recycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                    loadMoreData()
                }
            }
        })

        viewModel.attractionsResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    if (viewModel.pageCounter == 1) {
                        viewModel.clearAttractions()
                        adapter.notifyDataSetChanged()
                        binding.recycler.scrollToPosition(0)
                    }
                    isLoading = false
                    viewModel.addNewAttractions(it)
                    adapter.notifyItemInserted(viewModel.getAttractions().size -1)
                }
                Log.d(TAG, "response success")
            } else {
                Log.d(TAG, "response failed: ${response.code()}")
            }
        }

        viewModel.selectedItemPosition.observe(viewLifecycleOwner) { position ->
            if (position != null) {
               languageDialog.dismiss()
            }
            when(position) {
                0 -> binding.toolbarMain.title = "台北遊"
                1 -> binding.toolbarMain.title = "台北游"
                2 -> binding.toolbarMain.title = "TaipeiTour"
                3 -> binding.toolbarMain.title = "台北ツアー"
                4 -> binding.toolbarMain.title = "타이페이 투어"
                5 -> binding.toolbarMain.title = "gira por taipei"
                6 -> binding.toolbarMain.title = "Tur Taipei"
                7 -> binding.toolbarMain.title = "ทัวร์ไทเป"
                8 -> binding.toolbarMain.title = "du lịch Đài Bắc"
                else -> return@observe
            }
        }
    }

    private fun loadMoreData() {
        if (!isLoading) {
            isLoading = true
            viewModel.pageCounter++
            viewModel.fetchAttractions()
        }
    }
}