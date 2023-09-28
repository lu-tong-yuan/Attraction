package com.tommy.attractions.features.main.model

data class AttractionsResponse(
    val data : List<Attraction>,
    val total: Int
)