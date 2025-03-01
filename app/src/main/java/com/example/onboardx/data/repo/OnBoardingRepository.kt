package com.example.onboardx.data.repo

import com.example.onboardx.data.api.OnBoardingApiService
import com.example.onboardx.data.model.OnBoardingResponse
import com.example.onboardx.utils.ResourceWrapper
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(
    private val onBoardingApiService: OnBoardingApiService
) {
    suspend fun getOnBoardingData(): ResourceWrapper<OnBoardingResponse> {
        return try {
            val response = onBoardingApiService.getOnboardingData()

            if(response.isSuccessful){
                response.body()?.let {
                    ResourceWrapper.Success(it)
                } ?: ResourceWrapper.Failure(Exception("Empty Response"))
            } else {
                ResourceWrapper.Error(response.message(), response.code())
            }

        } catch (e : Exception){
            ResourceWrapper.Failure(e)
        }
    }

}