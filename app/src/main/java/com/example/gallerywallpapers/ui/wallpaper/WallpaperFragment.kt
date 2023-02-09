package com.example.gallerywallpapers.ui.wallpaper

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.gallerywallpapers.R
import com.example.gallerywallpapers.databinding.FragmentWallpaperBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class WallpaperFragment : Fragment() {

    private var _binding: FragmentWallpaperBinding? = null
    private val binding get() = _binding!!
    private var wallpaper: Bitmap? = null
    private var snackbar: Snackbar? = null

    private val args: WallpaperFragmentArgs by navArgs()
    private val viewModel: WallpaperViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWallpaperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupEvent()
        setupListeners()
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
                        Snackbar.LENGTH_LONG,
                    )
                    .also { it.show() }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupUi() = with(binding) {

        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                progressBar.visibility = if (!uiState) View.GONE else View.VISIBLE
            }
            .launchIn(lifecycle.coroutineScope)

        Glide
            .with(root)
            .asBitmap()
            .load(args.largeImageURL)
            .into(object : CustomTarget<Bitmap?>() {

                override fun onLoadStarted(placeholder: Drawable?) {
                    wallpaperView.setImageResource(R.drawable.image)
                    super.onLoadStarted(placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    wallpaperView.setImageResource(R.drawable.image_not_loaded)
                    super.onLoadFailed(errorDrawable)
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    wallpaperView.setImageBitmap(resource)
                    wallpaper = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            }
            )
    }

    private fun setupListeners() = with(binding) {
        buttonSetWallpaper.setOnClickListener { _ ->
            wallpaper?.let { viewModel.setWallpaper(it) }
        }
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
