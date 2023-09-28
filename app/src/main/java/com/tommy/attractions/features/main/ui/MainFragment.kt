package com.tommy.attractions.features.main.ui

import android.app.AlertDialog
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

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

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.setHasFixedSize(true)

        viewModel.attractionsResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                binding.recycler.adapter = response.body()?.data?.let {
                    AttractionAdapter(object : OnItemClickListener {
                        override fun onItemClick(attraction: Attraction) {
                            viewModel.updateAttraction(attraction)
                            NavHostFragment.findNavController(this@MainFragment).navigate(R.id.detailFragment)
                        }
                    },it)
                }
                Log.d(TAG, "response success");
            } else {
                Log.d(TAG, "response failed: ${response.code()}");
            }
        }

        viewModel.selectedItemPosition.observe(viewLifecycleOwner) { position ->
            if (position != null) {
               languageDialog.dismiss()
            }
        }
    }



}