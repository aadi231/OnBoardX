package com.example.onboardx.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onboardx.R
import com.example.onboardx.utils.ResourceWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardActivity : AppCompatActivity() {

    private val TAG = "OnBoardActivity"
    private val onBoardViewModel by viewModels<OnBoardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_on_board)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupObserver()
        fetchOnBoardData()
    }

    private fun fetchOnBoardData() {
        onBoardViewModel.getOnBoardUiData()
    }

    private fun setupObserver() {
        onBoardViewModel.onBoardUIData.observe(this) { response ->
            response?.let {
                when(it){
                    is ResourceWrapper.Success -> {

                    }
                    is ResourceWrapper.Failure -> {

                    }
                    is ResourceWrapper.Error -> {

                    }
                    is ResourceWrapper.Loading -> {

                    }
                }
            }
        }
    }
}