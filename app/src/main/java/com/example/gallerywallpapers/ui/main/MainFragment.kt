package com.example.gallerywallpapers.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.gallerywallpapers.R
import com.example.gallerywallpapers.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var snackbar: Snackbar? = null

    private val viewModel: MainViewModel by viewModels()

    private val onClickItem: (String) -> Unit = {
        findNavController().navigate(
            MainFragmentDirections.actionNavigationMainToNavigationGallery(it)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupEvent()
    }

    private fun setupUi() = with(binding) {

        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                progressBar.visibility = if (!uiState.isFetchingData) View.GONE else View.VISIBLE
                header.text = if (uiState.hasErrorState) getString(R.string.ui_text_header_error)
                else getString(R.string.ui_text_header_categories)

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    uiState.dataList
                )
                categoriesList.adapter = adapter
                categoriesList.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        onClickItem(uiState.dataList[position])
                    }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupEvent() {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                snackbar?.dismiss()
                snackbar = Snackbar
                    .make(
                        binding.root,
                        event,
                        Snackbar.LENGTH_INDEFINITE,
                    )
                    .setAction(R.string.snackbar_reload) { viewModel.reloadDataList() }
                    .also { it.show() }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    override fun onDestroyView() {
        snackbar?.let {
            it.dismiss()
            snackbar = null
        }
        _binding = null
        super.onDestroyView()
    }
}
