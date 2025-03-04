package com.example.onboardx.data.model

data class EducationCardData(
    val image : String,
    val collapsedStateText : String,
    val expandStateText : String,
    val backGroundColor : String,
    val strokeStartColor : String,
    val strokeEndColor : String,
    val startGradient : String,
    val endGradient : String,
    var isExpanded: Boolean? = false
)
