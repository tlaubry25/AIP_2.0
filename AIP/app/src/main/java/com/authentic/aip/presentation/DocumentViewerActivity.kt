package com.authentic.aip.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.documentViewer.DocumentViewerViewModel
import com.authentic.aip.presentation.listAttachments.ListAttachmentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentViewerActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.document_viewer)
        this.supportActionBar?.hide()
        ToolbarManager.setBackpress(this)
        val toolbarStatus = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), null)
        ToolbarManager.setDrawableByCodeStatus(this, toolbarStatus)
        val toolbarTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }
        initView()

    }

    private fun initView(){

    }
}