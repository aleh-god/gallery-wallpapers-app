package com.example.gallerywallpapers.ui.gallery

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gallerywallpapers.R
import com.example.gallerywallpapers.databinding.ItemGalleryBinding
import com.example.gallerywallpapers.domain.models.WallpaperModel

class ItemViewHolder(
    private val binding: ItemGalleryBinding,
    private val onClickPreview: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var itemUrl: String? = null

    init {
        binding.root.setOnClickListener { _ ->
            itemUrl?.let { onClickPreview(it) }
        }
    }

    fun bind(itemModel: WallpaperModel) {
        itemUrl = itemModel.largeImageURL
        itemModel.previewURL?.let {
            Glide.with(binding.root)
                .load(it)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.image_not_loaded)
                .placeholder(R.drawable.image)
                .into(binding.preview)
        }
    }
}
