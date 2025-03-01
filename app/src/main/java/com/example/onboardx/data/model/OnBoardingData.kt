package com.example.onboardx.data.model

data class OnBoardingData(
    val toolBarText : String,
    val introTitle : String,
    val introSubtitle : String,
    val educationCardList : List<EducationCardData>,
    val saveButtonCta : SaveButtonCtaData?,
    val ctaLottie : String,
    val screenType : String,
    val cohort : String,
    val combination : String,
    val collapseCardTiltInterval : Int,
    val collapseExpandIntroInterval : Int,
    val bottomToCenterTranslationInterval : Int,
    val expandCardStayInterval : Int,
    val seenCount : Int
)
