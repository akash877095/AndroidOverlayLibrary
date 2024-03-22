package com.overlaydemo.verticalviewpagercontrol

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overlaydemo.databinding.ItemVerticalPager1stBinding
import com.overlaydemo.databinding.ItemVerticalPagerBinding

class VerticalPagerAdapter() :
    RecyclerView.Adapter<VerticalPagerAdapter.ViewHolder2>() {

    private val data: MutableList<String> = mutableListOf()

    private lateinit var callback: View.OnClickListener

    fun setOnClickListener(callback: View.OnClickListener) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalPagerAdapter.ViewHolder2 {
        val binding = ItemVerticalPager1stBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder2(binding)
    }

    override fun onBindViewHolder(
        holder: VerticalPagerAdapter.ViewHolder2,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.itemView.alpha = 1f

        val itemData = data[position]
        holder.bind(itemData, position)

    }

    override fun getItemCount(): Int {
        Log.e("hfhfhfhfhfhfh", "getItemCount")
        return data.size
    }

    fun setData(dataList: List<String>) {
        Log.e("hfhfhfhfhfhfh", "setData")
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class ViewHolder2(binding: ItemVerticalPager1stBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val binding = binding

        fun bind(text: String, position: Int) {

            Log.e("hfhfhfhfhfhfh", "txert = $text")

            binding.tvTitleValue.text = text

            binding.firstViewRoot.setOnClickListener(callback)
            binding.firstViewRoot.tag = position

        }

    }

}