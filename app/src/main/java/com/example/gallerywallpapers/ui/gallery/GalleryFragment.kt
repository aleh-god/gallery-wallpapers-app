package com.example.gallerywallpapers.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.gallerywallpapers.R
import com.example.gallerywallpapers.databinding.FragmentGalleryBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private var snackbar: Snackbar? = null

    private val viewModel: GalleryViewModel by viewModels()

    private val onClickItem: (String) -> Unit = {
        findNavController().navigate(
            GalleryFragmentDirections.actionNavigationGalleryToNavigationWallpaper(it)
        )
    }

    private val adapter: GalleryAdapter by lazy { GalleryAdapter(onClickPreview = onClickItem) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupEvent()
    }

    private fun setupUi() = with(binding) {

        recyclerView.adapter = adapter

        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                progressBar.visibility = if (!uiState.isFetchingData) View.GONE else View.VISIBLE
                header.text = if (uiState.hasErrorState) getString(R.string.ui_text_header_error)
                else getString(R.string.ui_text_header_previews)
                adapter.submitList(uiState.dataList)
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
