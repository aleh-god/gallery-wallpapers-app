package com.example.gallerywallpapers.ui.gallery

import androidx.recyclerview.widget.DiffUtil
import com.example.gallerywallpapers.domain.models.WallpaperModel

class WallpaperModelDiffCallback : DiffUtil.ItemCallback<WallpaperModel>() {
    override fun areItemsTheSame(oldItem: WallpaperModel, newItem: WallpaperModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WallpaperModel, newItem: WallpaperModel): Boolean =
        oldItem == newItem
}
