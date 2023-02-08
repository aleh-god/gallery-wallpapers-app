package com.example.gallerywallpapers.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.gallerywallpapers.databinding.ItemGalleryBinding
import com.example.gallerywallpapers.domain.models.WallpaperModel

class GalleryAdapter(
    private val onClickPreview: (String) -> Unit
) : ListAdapter<WallpaperModel, ItemViewHolder>(WallpaperModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickPreview = onClickPreview
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
