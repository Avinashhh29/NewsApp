package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.ui.NewsActivity
import com.example.myapplication.ui.NewsViewModel

class SavedNewsFragment:Fragment(R.layout.fragment_saved_news) {

    private lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
    }
}