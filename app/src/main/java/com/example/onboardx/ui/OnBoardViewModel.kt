package com.example.onboardx.ui

import androidx.lifecycle.ViewModel
import com.example.onboardx.data.repo.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : ViewModel() {



}