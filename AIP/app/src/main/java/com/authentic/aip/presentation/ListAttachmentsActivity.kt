package com.authentic.aip.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attachments_list)
        loadAttachments()

        val rv_listRequest = findViewById<RecyclerView>(R.id.rv_attachments_list)
        rv_listRequest.layoutManager = GridLayoutManager(this, 2)
        adapter = ListAttachmentsAdapter(this, listOf(), this)

        listAttachmentsViewModel.listAttachmentsLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR") }
                it.listAttachments!=null ->{
                    Log.d("TLA", "STATE SUCCESS")
                    initview(it.listAttachments)
                }
            }
        }
    }
    private fun initview(listAttachments : ListAttachments){
        var listAttachmentsToFill : MutableList<Attachments> = mutableListOf()
        if(listAttachments.listAttachments!=null){
            for(attachment in listAttachments.listAttachments!!){
               if(attachment!=null){
                   listAttachmentsToFill.add(attachment)
               }
            }
        }
            adapter.setAttachmentsList(listAttachmentsToFill)
            adapter.notifyDataSetChanged()
    }

    private fun loadAttachments(){
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val requestId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if (sessionId != null && requestId != null) {
            listAttachmentsViewModel.listAttachments(sessionId, requestId, 0, pageNumber)
        }
    }
    override fun onAttachmentClick(attachment: Attachments?) {
        if(attachment!=null){
            /*val newActivityIntent = Intent(this, AttachmentActivity::class.java)
            startActivity(newActivityIntent)*/
        }
    }
}