package com.example.gallerywallpapers.ui.wallpaper

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.gallerywallpapers.R
import com.example.gallerywallpapers.databinding.FragmentWallpaperBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpaperFragment : Fragment() {

    private var _binding: FragmentWallpaperBinding? = null
    private val binding get() = _binding!!
    private var wallpaper: Bitmap? = null

    private val args: WallpaperFragmentArgs by navArgs()

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
        setupListeners()
    }

    private fun setupUi() = with(binding) {
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
            wallpaper?.let { setWallpaperAndShowMessage(it) }
        }
    }

    private fun setWallpaperAndShowMessage(resource: Bitmap) {
        val message = try {
            val wallpaperManager = WallpaperManager.getInstance(requireContext())
            wallpaperManager.setBitmap(resource)
            getString(R.string.message_wall_installed)
        } catch (e: Exception) {
            getString(R.string.message_wall_install_error)
        }
        Snackbar
            .make(
                binding.root,
                message,
                Snackbar.LENGTH_LONG,
            )
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
