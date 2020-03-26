package com.eratoiklio.quiztest

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OptionViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.option_list_item, parent, false)) {
    private var optionField: TextView? = null



    init {
        optionField = itemView.findViewById(R.id.option)

    }

    fun bind(option: String) {
        optionField?.text = option

    }

}