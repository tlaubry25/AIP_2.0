package com.authentic.aip.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.Attachments
import com.authentic.aip.domain.model.ListAttachments
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.listAttachments.ListAttachmentsViewModel
import com.authentic.aip.presentation.listAttachments.component.ListAttachmentsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListAttachmentsActivity:AppCompatActivity(), ListAttachmentsAdapter.ItemClickListener{
    private val listAttachmentsViewModel: ListAttachmentsViewModel by viewModels()
    private lateinit var adapter : ListAttachmentsAdapter
    private var pageNumber = 1
    var deli : Int?=0
    var attachmentType : String? = null
    var attachmentTitle : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attachments_list)
        this.supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        ToolbarManager.setBackpress(this)
        val toolbarStatus = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), null)
        ToolbarManager.setDrawableByCodeStatus(this, toolbarStatus)
        val toolbarTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }
        val intent = intent
        deli = intent.getIntExtra("deli", 0)
        loadAttachments()

        val rv_listAttachments = findViewById<RecyclerView>(R.id.rv_attachments_list)
        rv_listAttachments.layoutManager = GridLayoutManager(this, 2)
        adapter = ListAttachmentsAdapter(this, listOf(), this)
        rv_listAttachments.adapter = adapter

        listAttachmentsViewModel.listAttachmentsLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.listAttachments!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE SUCCESS")
                    initview(it.listAttachments)
                }
            }
        }
/*        listAttachmentsViewModel.getAttachmentsLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.attachmentData!=null ->{
                    Log.d("TLA", "STATE SUCCESS")
                    progressBar.visibility = View.GONE
                    val newActivityIntent = Intent(this, DocumentViewerActivity::class.java)
                    newActivityIntent.putExtra("contentType", attachmentType)
                    newActivityIntent.putExtra("contentData", it.attachmentData)
                    newActivityIntent.putExtra("contentTitle", attachmentTitle)
                    startActivity(newActivityIntent)
                }
            }
        }*/
        rv_listAttachments.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1)){
                    callPagination()
                }
            }
        })
    }
    private fun initview(listAttachments : ListAttachments){
        var listAttachmentsToFill : MutableList<Attachments> = mutableListOf()
        if(listAttachments.listAttachments!=null){
            for(attachment in listAttachments.listAttachments){
               if(attachment!=null){
                   listAttachmentsToFill.add(attachment)
               }
            }
        }
            adapter.setAttachmentsList(listAttachmentsToFill)
            adapter.notifyDataSetChanged()
    }
    private fun callPagination(){
        pageNumber++
        loadAttachments()
    }

    private fun loadAttachments(){
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val requestId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if (sessionId != null && requestId != null) {
            if(deli!=null){
                listAttachmentsViewModel.listAttachments(this, sessionId, requestId, deli!!, pageNumber)
            }else{
                listAttachmentsViewModel.listAttachments(this, sessionId, requestId, 0, pageNumber)
            }

        }
    }
    override fun onAttachmentClick(attachment: Attachments?) {
        if(attachment!=null){
            if(!attachment.type.isNullOrEmpty()){
                val newActivityIntent = Intent(this, DocumentViewerActivity::class.java)
                newActivityIntent.putExtra("attachment", attachment)
                startActivity(newActivityIntent)
            }else{
                MessageManager.showToast(this, R.string.attachment_format_incompatible)
            }
        }
    }
}