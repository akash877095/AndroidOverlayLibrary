package com.overlaydemo.utils.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.overlaydemo.databinding.DialogViewpagerItemBinding

class DialogViewPagerAdapter(val isBannerVisible: Boolean,val isAllPromoVisible: Boolean) :
    RecyclerView.Adapter<DialogViewPagerAdapter.ViewHolder>() {

    private val data: MutableList<String> = mutableListOf()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DialogViewpagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        Log.e("hfhfhfhfhfhfh", "onCreateViewHolder")
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = data[position]
        Log.e("hfhfhfhfhfhfh", "onBindViewHolder")
        holder.bind(itemData)
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

    inner class ViewHolder(binding: DialogViewpagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding = binding
        fun bind(data: String) {
            Log.e("hfhfhfhfhfhfh", "txert = $data")

            if (!isBannerVisible) {
                binding.ivBannerImg.visibility = View.GONE
                val layoutParam = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParam.setMargins(
                    context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._15sdp), 0,
                    context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._15sdp), 0
                )
                binding.cvPopupView.layoutParams = layoutParam
            }

            if(!isAllPromoVisible) binding.btnViewAllPromo.visibility = View.GONE

        }

    }

}