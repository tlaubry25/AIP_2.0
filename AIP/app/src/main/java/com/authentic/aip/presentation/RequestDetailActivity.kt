package com.authentic.aip.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.DedLine
import com.authentic.aip.domain.model.RequestDetail
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.requestDetail.RequestDetailViewModel
import com.authentic.aip.presentation.requestDetail.component.RequestDetailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestDetailActivity:AppCompatActivity(), RequestDetailAdapter.ItemClickListener {
    private val requestDetailViewModel: RequestDetailViewModel by viewModels()
    private lateinit var adapter : RequestDetailAdapter
    private var pageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_detail)
        this.supportActionBar?.hide()
        ToolbarManager.setBackpress(this)
        val toolbarStatus = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), null)
        ToolbarManager.setDrawableByCodeStatus(this, toolbarStatus)
        val toolbarTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }
        loadDedlines()

        val rv_listDedline = findViewById<RecyclerView>(R.id.rv_request_detail)
        rv_listDedline.layoutManager = LinearLayoutManager(this)
        adapter = RequestDetailAdapter(this, listOf(), this)
        rv_listDedline.adapter = adapter

        rv_listDedline.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1)){
                    callPagination()
                }
            }
        })

        requestDetailViewModel.requestDetailLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR") }
                it.requestDetailData!=null ->{
                    Log.d("TLA", "STATE SUCCESS")

                    initview(it.requestDetailData)
                }
            }
        }
    }

    private fun loadDedlines(){
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val cddeid = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if (sessionId != null && cddeid != null) {
            requestDetailViewModel.requestDetail(sessionId, cddeid, '0', null, pageNumber)
        }
    }

    private fun callPagination(){
        pageNumber++
        loadDedlines()
    }

    private fun initview(requestDetail: RequestDetail){
        var listDedlineToFill : MutableList<DedLine> = mutableListOf()
        if(requestDetail.listDedLine!=null){
            for(dedline in requestDetail.listDedLine!!){
                if(dedline!=null){
                    listDedlineToFill.add(dedline)
                }
            }
        }
        adapter.setDedLineList(listDedlineToFill)
        adapter.notifyDataSetChanged()

    }

    override fun onNotesClick(dedLine: DedLine?) {
        if(dedLine!=null){
            //if(dedLine.nbNotes>0){
                val newActivityIntent = Intent(this, NotesListActivity::class.java)
                newActivityIntent.putExtra("deli", dedLine.deli)
                startActivity(newActivityIntent)
            //}
        }
    }

    override fun onAttachmentClick(dedLine: DedLine?) {
        if(dedLine!=null){
            //if(dedLine.nbDocs>0){
                val newActivityIntent = Intent(this, ListAttachmentsActivity::class.java)
                newActivityIntent.putExtra("deli", dedLine.deli)
                startActivity(newActivityIntent)
            //}
        }
    }

    override fun onTextClick(dedLine: DedLine?) {
        if(dedLine!=null){
            //(dedLine.existText){
                val newActivityIntent = Intent(this, RequestTextActivity::class.java)
                newActivityIntent.putExtra("deli", dedLine.deli)
                newActivityIntent.putExtra("requestTextTitle", dedLine.itds)
                startActivity(newActivityIntent)
            //}
        }
    }
}