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
            0 -> viewModel.fetchAttractions("zh-tw", page = 1)
            1 -> viewModel.fetchAttractions("zh-cn", page = 1)
            2 -> viewModel.fetchAttractions("en", page = 1)
            3 -> viewModel.fetchAttractions("ja", page = 1)
            4 -> viewModel.fetchAttractions("ko", page = 1)
            5 -> viewModel.fetchAttractions("es", page = 1)
            6 -> viewModel.fetchAttractions("id", page = 1)
            7 -> viewModel.fetchAttractions("th", page = 1)
            8 -> viewModel.fetchAttractions("vi", page = 1)
            else -> return
        }
    }
}

class LanguageHolder(view: View) : RecyclerView.ViewHolder(view) {
    var lanText : TextView = view.findViewById(R.id.lan)
}