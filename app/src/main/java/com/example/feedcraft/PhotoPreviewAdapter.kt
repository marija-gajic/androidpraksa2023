package com.example.feedcraft

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoPreviewAdapter (var items : MutableList<PhotoPreviewModel>, val onClick: (Int, Boolean) -> Unit)
    : RecyclerView.Adapter<PhotoPreviewAdapter.CustomViewHolder>(){
    //private lateinit var binding: ItemPhotoPreviewBinding
    private var lastSelectedIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoPreviewAdapter.CustomViewHolder {
        Log.d("mylog", "onCreateViewHolder called")
        val inflater = LayoutInflater.from(parent.context)
        //binding = ItemPhotoPreviewBinding.inflate(inflater,parent,false)
        //return CustomViewHolder(binding)
        return CustomViewHolder(inflater.inflate(R.layout.item_photo_preview, parent, false))
    }
    override fun onBindViewHolder(holder: PhotoPreviewAdapter.CustomViewHolder, position: Int) {
        Log.d("mylog", "onBindViewHolder called $position")

        if(lastSelectedIndex == position)
        {
            holder.previewPhotoChecked.isVisible = true
        }
        else
        {
            holder.previewPhotoChecked.isVisible = false
        }

        onClick(position, false)

        //val checkedPhoto = holder.previewPhotoChecked
        holder.itemView.setOnClickListener {

            if(lastSelectedIndex != position) {
                lastSelectedIndex = position
                //holder.previewPhotoChecked.isVisible = !holder.previewPhotoChecked.isVisible
                onClick(position, true)
                notifyDataSetChanged()
                //checkedPhoto.isVisible = !checkedPhoto.isVisible
            }
            else
            {
                onClick(position, false)
            }
        }

        holder.previewBorder.setBackgroundColor(items[position].borderColor)
        //holder.previewPhoto.setImageBitmap(items[position].previewBitmap)

        Glide.with(holder.itemView.context).load(items[position].previewBitmap).into(holder.previewPhoto)

    }
    override fun getItemCount() = items.size


    fun refreshFilterAdapterList(items: MutableList<PhotoPreviewModel>)
    {
        this.items = items
        notifyDataSetChanged()
    }


    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var previewBorder = itemView.findViewById<ImageView>(R.id.preview_border)
        var previewPhoto = itemView.findViewById<ImageView>(R.id.preview_photo)
        var previewPhotoChecked = itemView.findViewById<ImageView>(R.id.preview_photo_checked)

    }
}