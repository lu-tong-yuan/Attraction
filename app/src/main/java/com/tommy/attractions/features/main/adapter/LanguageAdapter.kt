package com.tommy.attractions.features.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tommy.attractions.R
import com.tommy.attractions.features.main.ui.MainViewModel

val languages = listOf(
    "繁體中文",
    "简体中文",
    "English",
    "Japanese",
    "Korean",
    "spanish",
    "Indonesian",
    "Thai",
    "Vietnamese")

class LanguageAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<LanguageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_language, parent, false)
        return LanguageHolder(view)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        holder.lanText.text = languages[position]
        holder.itemView.setOnClickListener{
            functionClicked(position)
            viewModel.selectedItemPosition.value = position
        }
    }

    private fun functionClicked(position: Int) {
        when(position) {
            0 -> viewModel.fetchAttractions("zh-tw")
            1 -> viewModel.fetchAttractions("zh-cn")
            2 -> viewModel.fetchAttractions("en")
            3 -> viewModel.fetchAttractions("ja")
            4 -> viewModel.fetchAttractions("ko")
            5 -> viewModel.fetchAttractions("es")
            6 -> viewModel.fetchAttractions("id")
            7 -> viewModel.fetchAttractions("th")
            8 -> viewModel.fetchAttractions("vi")
            else -> return
        }
    }
}

class LanguageHolder(view: View) : RecyclerView.ViewHolder(view) {
    var lanText : TextView = view.findViewById(R.id.lan)
}