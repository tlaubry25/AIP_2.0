package com.authentic.aip.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.authentic.aip.R
import com.authentic.aip.presentation.documentViewer.DocumentViewerViewModel
import com.authentic.aip.presentation.listAttachments.ListAttachmentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentViewerActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.document_viewer)

        initView()

    }

    private fun initView(){

    }
}