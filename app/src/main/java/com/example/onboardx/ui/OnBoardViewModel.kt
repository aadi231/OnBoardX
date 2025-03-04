package com.example.onboardx.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboardx.data.model.OnBoardingResponse
import com.example.onboardx.data.repo.OnBoardingRepository
import com.example.onboardx.utils.ResourceWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : ViewModel() {

    private val _onBoardUIData = MutableLiveData<ResourceWrapper<OnBoardingResponse>>()
    val onBoardUIData : LiveData<ResourceWrapper<OnBoardingResponse>> = _onBoardUIData

    private val _onBoardUIDataError = MutableLiveData<ResourceWrapper<Nothing>>()
    val onBoardUIDataError : LiveData<ResourceWrapper<Nothing>> = _onBoardUIDataError

    fun getOnBoardUiData(){
        viewModelScope.launch {
            _onBoardUIData.postValue(ResourceWrapper.Loading)

            val onBoardResult = withContext(Dispatchers.IO) {
                onBoardingRepository.getOnBoardingData()
            }

            _onBoardUIData.postValue(onBoardResult)
        }
    }
}