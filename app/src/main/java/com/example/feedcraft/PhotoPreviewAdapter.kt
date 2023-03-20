package com.example.feedcraft

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.feedcraft.databinding.ItemPhotoPreviewBinding

class PhotoPreviewAdapter (val items : MutableList<PhotoPreviewModel>, val onClick: (Int) -> Unit)
    : RecyclerView.Adapter<PhotoPreviewAdapter.ViewHolder>(){
    private lateinit var binding: ItemPhotoPreviewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoPreviewAdapter.ViewHolder {
        Log.d("mylog", "onCreateViewHolder called")
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemPhotoPreviewBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PhotoPreviewAdapter.ViewHolder, position: Int) {
        Log.d("mylog", "onBindViewHolder called $position")
        val checkedPhoto = binding.previewPhotoChecked
        holder.itemView.setOnClickListener {
            onClick(position)
            checkedPhoto.isVisible = !checkedPhoto.isVisible
        }

//        holder.itemView.setOnFocusChangeListener { v, hasFocus ->
//            if(v.hasFocus()) {
//                Log.d("mylog","item $position has focus")
//            }
//            else {}
//        }

        holder.bind(items[position])
    }
    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: ItemPhotoPreviewBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : PhotoPreviewModel){

            binding.apply {
                previewBorder.setBackgroundColor(item.borderColor)
                previewPhoto.setImageBitmap(item.previewBitmap)

            }
        }
    }
}