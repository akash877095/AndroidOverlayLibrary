package com.overlaydemo.verticalviewpagercontrol

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overlaydemo.databinding.ItemVerticalPagerBinding

class VerticalPagerAdapterSecond() :
    RecyclerView.Adapter<VerticalPagerAdapterSecond.ViewHolder>() {

    private val data: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalPagerAdapterSecond.ViewHolder {
        val binding = ItemVerticalPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: VerticalPagerAdapterSecond.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val itemData = data[position]
        holder.bind(itemData, position)

    }

    override fun getItemCount(): Int {
        Log.e("hfhfhfhfhfhfh", "getItemCount ")
        return data.size
    }

    fun setData(dataList: List<String>) {
        Log.e("hfhfhfhfhfhfh", "setData")
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemVerticalPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val binding = binding

        fun bind(text: String, position: Int) {

            Log.e("hfhfhfhfhfhfh", "txert = $text")

            binding.tvTitle.text = text

        }

    }

}