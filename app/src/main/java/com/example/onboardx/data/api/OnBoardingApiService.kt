package com.example.onboardx.data.api

import com.example.onboardx.data.model.OnBoardingResponse
import retrofit2.Response
import retrofit2.http.GET

interface OnBoardingApiService {
    @GET("v3/0a095cf2-a081-44af-965a-953b0fa6499b")
    suspend fun getOnboardingData() : Response<OnBoardingResponse>
}