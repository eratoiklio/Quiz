package com.eratoiklio.quiztest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListAdapter()
    : RecyclerView.Adapter<OptionViewHolder>() {
    private var list: List<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OptionViewHolder(inflater, parent)
    }
    fun setOptions(options : List<String>){
        list = options
    }
    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option: String = list[position]
        holder.bind(option)
    }

    override fun getItemCount(): Int = list.size

}