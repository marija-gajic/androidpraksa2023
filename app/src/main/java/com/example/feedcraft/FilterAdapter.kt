package com.example.feedcraft

import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.feedcraft.databinding.ItemFilterPreviewBinding

class FilterAdapter (val items : MutableList<FilterModel>)
    : RecyclerView.Adapter<FilterAdapter.ViewHolder>(){
    private lateinit var binding: ItemFilterPreviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding=ItemFilterPreviewBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    override fun getItemCount() = items.size
    inner class ViewHolder(itemView : ItemFilterPreviewBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : FilterModel){

            binding.apply {
                filterName.text = item.name
                filterBox.setImageBitmap(item.filterBitmap)
            }
        }
    }
}