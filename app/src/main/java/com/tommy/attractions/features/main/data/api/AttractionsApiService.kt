package com.tommy.attractions.features.main.data.api

import com.tommy.attractions.features.main.model.AttractionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AttractionsApiService {
    @GET("{lang}/Attractions/All")
    suspend fun getAttractions(
        @Path("lang") lang: String,
        @Query("categoryIds") categoryIds: String?,
        @Query("nlat") nlat: Double?,
        @Query("elong") elong: Double?,
        @Query("page") page: Int?
    ) : Response<AttractionsResponse>
}