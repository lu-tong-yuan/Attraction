package com.tommy.attractions.features.main

import com.tommy.attractions.features.main.model.Attraction

interface OnItemClickListener {
    fun onItemClick(attraction: Attraction)
}