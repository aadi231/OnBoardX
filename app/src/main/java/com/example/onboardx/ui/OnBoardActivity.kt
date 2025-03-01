package com.example.onboardx.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onboardx.R
import com.example.onboardx.animation.RecyclerViewItemAnimator
import com.example.onboardx.data.model.EducationCardData
import com.example.onboardx.data.model.OnBoardingData
import com.example.onboardx.data.model.SaveButtonCtaData
import com.example.onboardx.databinding.ActivityOnBoardBinding
import com.example.onboardx.ui.adapters.OnboardItemAdapter
import com.example.onboardx.utils.BackgroundWithRadialGradient
import com.example.onboardx.utils.Utils.fadeIn
import com.example.onboardx.utils.Utils.fadeOut
import com.example.onboardx.utils.ResourceWrapper
import com.example.onboardx.utils.Utils.setGradientBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardActivity : AppCompatActivity() {

    private val TAG = "OnBoardActivity"
    private val onBoardViewModel by viewModels<OnBoardViewModel>()
    private lateinit var onboardBinding : ActivityOnBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onboardBinding = ActivityOnBoardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(onboardBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupObserver()
        fetchOnBoardData()
        setupClickListener()
    }

    private fun setupClickListener() {
        onboardBinding.saveButton.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.activity_fade_in_anim, R.anim.activity_slide_up_anim)
            startActivity(intent, options.toBundle())
            finish()
        }
    }

    private fun setupRecyclerViewAdapter(educationCardList: List<EducationCardData>) {
        onboardBinding.onboardingRecyclerView.apply {
            visibility = View.VISIBLE
            adapter = OnboardItemAdapter(educationCardList).apply {
                onItemAnimationStart = onAnimationStart
            }
            layoutManager = LinearLayoutManager(this@OnBoardActivity, RecyclerView.VERTICAL, false)
        }
    }

    private fun fetchOnBoardData() {
        onBoardViewModel.getOnBoardUiData()
    }

    private fun setupObserver() {
        onBoardViewModel.onBoardUIData.observe(this) { response ->
            response?.let {
                when(it){
                    is ResourceWrapper.Success -> {
                        onboardBinding.welcomeToText.fadeOut(500)
                        onboardBinding.onboardingText.fadeOut(500)
                        onboardBinding.onboardAppBar.fadeIn(500)

                        populateViews(it.data.data.onboardingData)
                    }
                    is ResourceWrapper.Failure -> {
                        Toast.makeText(this, "${it.exception.message}", Toast.LENGTH_SHORT).show()
                    }
                    is ResourceWrapper.Error -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResourceWrapper.Loading -> {
                    }
                }
            }
        }
    }

    private fun populateViews(onboardingData: OnBoardingData) {
        onboardingData.saveButtonCta?.let { loadCtaData(it, onboardingData.ctaLottie) }
        setupRecyclerViewAdapter(onboardingData.educationCardList)
    }

    private fun loadCtaData(saveButtonCta: SaveButtonCtaData, ctaLottie: String) {
        onboardBinding.saveText.text = saveButtonCta.text
        onboardBinding.saveButtonLottie.setAnimationFromUrl(ctaLottie)
        onboardBinding.saveButton.fadeIn(500)
    }

    private val onAnimationStart = { backgroundColor : String, endGradient: String, startGradient: String ->
        //onboardBinding.root.setGradientBackground(backgroundColor, endGradient, startGradient)

        val bgColor = Color.parseColor("#$backgroundColor") // Background color
        val end = Color.parseColor(endGradient)
        val gradientDrawable = BackgroundWithRadialGradient(bgColor, end)
        onboardBinding.root.background = gradientDrawable
    }
}