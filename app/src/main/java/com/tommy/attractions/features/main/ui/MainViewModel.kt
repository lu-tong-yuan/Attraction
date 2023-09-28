package com.tommy.attractions.features.main.ui

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
    init {
        fetchAttractions("zh-tw")
    }

    fun fetchAttractions(lang: String, categoryIds: String? = null, nlat: Double? = null, elong: Double? = null, page: Int? = null) {
        viewModelScope.launch {
            val response = attractionsRepository.getAttractions(lang,categoryIds,nlat,elong,page)
            attractionsResponse.value = response
        }
    }

    fun updateAttraction(att: Attraction) {
        attraction.postValue(att)
    }
}