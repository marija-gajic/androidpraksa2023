package com.example.feedcraft.recyclerViewAdapters

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedcraft.recyclerViewModels.FilterModel
import com.example.feedcraft.R

class FilterAdapter (var items : MutableList<FilterModel>, val onClick: (Int) -> Unit)
    : RecyclerView.Adapter<FilterAdapter.CustomViewHolder>(){

    private var lastSelectedIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(inflater.inflate(R.layout.item_filter_preview, parent, false))
    }
    override fun onBindViewHolder(holder: CustomViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.filterName.text = items[position].name
        holder.filterPreview.setImageBitmap(items[position].filterBitmap)

        holder.filterName.typeface = Typeface.DEFAULT

        if(lastSelectedIndex == position)
        {//trenutno selektovani
            holder.filterName.typeface = Typeface.DEFAULT_BOLD
            holder.filterName.setTextColor(holder.itemView.context.resources.getColor(R.color.buttons))
        }
        else
        {//nije selektovan
            holder.filterName.typeface = Typeface.DEFAULT
            holder.filterName.setTextColor(holder.itemView.context.resources.getColor(R.color.icon_text))
        }


        holder.itemView.setOnClickListener {
            lastSelectedIndex = position
            onClick(position)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount() = items.size
    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var filterName = itemView.findViewById<TextView>(R.id.filter_name)
        var filterPreview = itemView.findViewById<ImageView>(R.id.filter_box)
    }
}