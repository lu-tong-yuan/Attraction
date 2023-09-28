package com.tommy.attractions.features.main.data.repository

import com.tommy.attractions.features.main.data.api.AttractionsApiService
import com.tommy.attractions.features.main.model.AttractionsResponse
import retrofit2.Response
import javax.inject.Inject

class AttractionsRepository @Inject constructor(private val attractionsApiService: AttractionsApiService){
    suspend fun getAttractions(lang: String, categoryIds: String?, nlat: Double?, elong: Double?, page: Int?): Response<AttractionsResponse> {
        return attractionsApiService.getAttractions(lang,categoryIds,nlat,elong,page)
    }
}