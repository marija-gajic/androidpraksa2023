package com.example.feedcraft

import android.graphics.Bitmap
import android.graphics.Typeface
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.feedcraft.databinding.ItemFilterPreviewBinding

class FilterAdapter (val items : MutableList<FilterModel>, val onClick: (Int) -> Unit)
    : RecyclerView.Adapter<FilterAdapter.ViewHolder>(){
    private lateinit var binding: ItemFilterPreviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemFilterPreviewBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onClick(position)
            items[position].selected.apply { not() }
            //notifyItemChanged(position)
            //notifyDataSetChanged()
        }
        holder.bind(items[position])
    }
    override fun getItemCount() = items.size
    inner class ViewHolder(itemView : ItemFilterPreviewBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : FilterModel){

            binding.apply {
                filterName.text = item.name
                filterName.typeface = if (item.selected) Typeface.DEFAULT else Typeface.DEFAULT_BOLD
                filterBox.setImageBitmap(item.filterBitmap)
            }
        }
    }
}