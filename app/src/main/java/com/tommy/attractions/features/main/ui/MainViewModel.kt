package com.tommy.attractions.features.main.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommy.attractions.features.main.data.repository.AttractionsRepository
import com.tommy.attractions.features.main.model.Attraction
import com.tommy.attractions.features.main.model.AttractionsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val attractionsRepository: AttractionsRepository
) : ViewModel() {
    var attractionsResponse = MutableLiveData<Response<AttractionsResponse>>()
    var selectedItemPosition = MutableLiveData<Int>()
    var attraction = MutableLiveData<Attraction>()
    private val attractions = mutableListOf<Attraction>()
    var pageCounter = 1
    private var language = "zh-tw"
    init {
        fetchAttractions(language)
    }

    fun fetchAttractions(lang: String? = language, categoryIds: String? = null, nlat: Double? = null, elong: Double? = null, page: Int? = pageCounter) {
        viewModelScope.launch {
            if (lang != null) {
                language = lang
            }
            if (page != null) {
                pageCounter = page
            }
            Log.d("TAG_COUNT", page.toString())
            val response = attractionsRepository.getAttractions(lang!!,categoryIds,nlat,elong,page)
            attractionsResponse.value = response
        }
    }

    fun updateAttraction(att: Attraction) {
        attraction.postValue(att)
    }

    fun getAttractions(): List<Attraction> {
        return attractions
    }

    // 添加新项到数据集中
    fun addNewAttractions(atts: List<Attraction>) {
        attractions.addAll(atts)
    }

    fun clearAttractions() {
        attractions.clear()
    }
}