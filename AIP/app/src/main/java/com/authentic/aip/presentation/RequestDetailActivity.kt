package com.authentic.aip.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
    private var originalOrder : Int = 0
    private var requestModified = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_detail)
        this.supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        ToolbarManager.setBackpress(this)

        val intent = intent
        originalOrder = intent.getIntExtra("originalOrder", 0)

        /*val clBackpressToolbar = findViewById<ConstraintLayout>(R.id.cl_content_toolbar)
        clBackpressToolbar.setOnClickListener {
            if(originalOrder == 0){
                onBackPressed()
            }else{
                val newActivityIntent = Intent(this, RequestDetailActivity::class.java)
                newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                newActivityIntent.putExtra("originalOrder",0)
                startActivity(newActivityIntent)
            }

        }*/
        val clNavigation = findViewById<ConstraintLayout>(R.id.cl_custom_navigation_bar)
        val btNavigation = findViewById<Button>(R.id.button_navigation)
        val tvNavigation = findViewById<TextView>(R.id.tv_navigation_title)
        if(originalOrder == 0){
            clNavigation.visibility = View.GONE
            btNavigation.background = ContextCompat.getDrawable(this, R.drawable.historique)
            tvNavigation.text = this.getString(R.string.navigation_original_request)
        }else{
            clNavigation.visibility = View.VISIBLE
            btNavigation.background = ContextCompat.getDrawable(this, R.drawable.historique)
            tvNavigation.text = this.getString(R.string.navigation_modified_request)
        }
        val toolbarStatus = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), null)
        ToolbarManager.setDrawableByCodeStatus(this, toolbarStatus)
        val toolbarTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
            if(toolbarTitle[0].equals("*")){
                val tvDetailModification = findViewById<TextView>(R.id.tv_section_modification_detail)
                tvDetailModification.visibility = View.VISIBLE
                tvDetailModification.setOnClickListener {
                    val newActivityIntent = Intent(this, RequestModificationDetailActivity::class.java)
                    startActivity(newActivityIntent)
                }
            }
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
                    progressBar.visibility = View.VISIBLE
                    Log.d("AIP", "call requestDetail STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call requestDetail STATE ERROR") }
                it.requestDetailData!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call requestDetail STATE SUCCESS")

                    initview(it.requestDetailData)
                }
            }
        }

        btNavigation.setOnClickListener {
            val newActivityIntent = Intent(this, RequestDetailActivity::class.java)
            newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if(originalOrder == 0){
                newActivityIntent.putExtra("originalOrder",1)
            }else{
                newActivityIntent.putExtra("originalOrder",0)
            }
            startActivity(newActivityIntent)
        }
    }

    private fun loadDedlines(){
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val cddeid = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if (sessionId != null && cddeid != null) {
            requestDetailViewModel.requestDetail(this, sessionId, cddeid, '0', originalOrder, pageNumber)
        }
    }

    private fun callPagination(){
        pageNumber++
        loadDedlines()
    }

    private fun initview(requestDetail: RequestDetail){
        var listDedlineToFill : MutableList<DedLine> = mutableListOf()
        if(requestDetail.listDedLine!=null){
            for(dedline in requestDetail.listDedLine){
                if(dedline!=null){
                    if(dedline.requestType=="M"){
                        requestModified = true
                    }
                    listDedlineToFill.add(dedline)
                }
            }
        }
        adapter.setDedLineList(listDedlineToFill)
        adapter.notifyDataSetChanged()
        if(requestModified){
            val clNavigation = findViewById<ConstraintLayout>(R.id.cl_custom_navigation_bar)
            clNavigation.visibility = View.VISIBLE
        }
    }

    override fun onNotesClick(dedLine: DedLine?) {
        if(dedLine!=null){
                val newActivityIntent = Intent(this, NotesListActivity::class.java)
                newActivityIntent.putExtra("deli", dedLine.deli)
                startActivity(newActivityIntent)
        }
    }

    override fun onAttachmentClick(dedLine: DedLine?) {
        if(dedLine!=null){
                val newActivityIntent = Intent(this, ListAttachmentsActivity::class.java)
                newActivityIntent.putExtra("deli", dedLine.deli)
                startActivity(newActivityIntent)
        }
    }

    override fun onTextClick(dedLine: DedLine?) {
        if(dedLine!=null){
                val newActivityIntent = Intent(this, RequestTextActivity::class.java)
                newActivityIntent.putExtra("deli", dedLine.deli)
                newActivityIntent.putExtra("requestTextTitle", dedLine.itds)
                startActivity(newActivityIntent)
        }
    }

    override fun onDetailModification(dedLine: DedLine?) {
        if(dedLine!=null){
            val newActivityIntent = Intent(this, RequestModificationDetailActivity::class.java)
            newActivityIntent.putExtra("deli", dedLine.deli)
            newActivityIntent.putExtra("lineType", dedLine.lineType)
            startActivity(newActivityIntent)
        }
    }
}