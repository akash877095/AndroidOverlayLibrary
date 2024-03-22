package com.overlaydemo.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overlaydemo.databinding.ItemsLayoutBinding

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    private var mListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setOnClickListener(aListener: View.OnClickListener) {
        mListener = aListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ViewHolder(private val binding: ItemsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
