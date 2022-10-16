package com.authentic.aip.presentation

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
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
import com.authentic.aip.domain.model.ListRequest
import com.authentic.aip.domain.model.POs
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.listRequest.ListRequestViewModel
import com.authentic.aip.presentation.listRequest.component.RequestListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListRequestActivity : AppCompatActivity(), RequestListAdapter.ItemClickListener {
    private val listRequestViewModel: ListRequestViewModel by viewModels()
    private lateinit var adapter : RequestListAdapter
    private var pageNumber = 1
    private var typeView : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.requests_list)
        this.supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
//        val intent = intent
        typeView = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TYPE.toString(), null)
//        typeView = intent.getStringExtra("typeRequest")
        val btNavigation = findViewById<Button>(R.id.button_navigation)
        val tvNavigation = findViewById<TextView>(R.id.tv_navigation_title)
        if(typeView == EnumClass.TypeRequestEnum.ONGOING.toString()){
            btNavigation.background = ContextCompat.getDrawable(this, R.drawable.historique)
            tvNavigation.text = this.getString(R.string.navigation_historical)
            ToolbarManager.setTitleText(this, getString(R.string.toolbar_ongoing))
            ToolbarManager.setDrawable(this, R.drawable.approbation_white)
        }else{
            btNavigation.background = ContextCompat.getDrawable(this, R.drawable.approbation)
            tvNavigation.text = this.getString(R.string.navigation_ongoing)
            ToolbarManager.setTitleText(this, getString(R.string.toolbar_historical))
            ToolbarManager.setDrawable(this, R.drawable.historique_blanc)
        }
        ToolbarManager.setBackpress(this)
        loadRequests()
        val rv_listRequest = findViewById<RecyclerView>(R.id.rv_request_list)
        rv_listRequest.layoutManager = LinearLayoutManager(this)
        adapter = RequestListAdapter(this, listOf(), this, typeView)
        rv_listRequest.adapter = adapter

        rv_listRequest.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1)){
                    callPagination()
                }
            }
        })

        listRequestViewModel.listRequestLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.listRequest!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE SUCCESS")

                    initview(it.listRequest)
                }
            }
        }

        btNavigation.setOnClickListener {
            val uidEditor = App.prefs?.preferences?.edit()
            if(typeView == EnumClass.TypeRequestEnum.ONGOING.toString()){
                uidEditor?.putString(EnumClass.PreferencesEnum.REQUEST_TYPE.toString(), EnumClass.TypeRequestEnum.DONE.toString())
                uidEditor?.commit()
            }else{
                uidEditor?.putString(EnumClass.PreferencesEnum.REQUEST_TYPE.toString(), EnumClass.TypeRequestEnum.ONGOING.toString())
                uidEditor?.commit()
            }
            val newActivityIntent = Intent(this, ListRequestActivity::class.java)
            newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(newActivityIntent)
        }

    }

    private fun initview(listRequest: ListRequest){
        var listRequestToFill : MutableList<POs> = mutableListOf()
        if(listRequest.listPOs!=null){
            for(po in listRequest.listPOs!!){
                if(po!=null){
                    listRequestToFill.add(po)
                }
            }
        }
        adapter.setRequestList(listRequestToFill)
        adapter.notifyDataSetChanged()
        if(pageNumber==1){
            callPagination()
        }
    }

    private fun loadRequests(){
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)

        if (sessionId != null && typeView!=null) {
            if(typeView == EnumClass.TypeRequestEnum.ONGOING.toString()){
                listRequestViewModel.listRequest(sessionId, '0', 0, pageNumber)
            }else{
                listRequestViewModel.listRequest(sessionId, '0', 1, pageNumber)
            }
            //listRequestViewModel.listRequest(sessionId, '0', false, pageNumber)
        }
    }
    private fun callPagination(){
        pageNumber++
        loadRequests()
    }

    override fun onRequestClick(request: POs?) {
        Log.d("TLA", "OnRequestClick Request")
        if(request!=null){
            val uidEditor = App.prefs?.preferences?.edit()
            uidEditor?.putString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), request.cddeid)
            uidEditor?.putString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), request.objt)
            uidEditor?.putString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), request.dest)
            uidEditor?.commit()

            val newActivityIntent = Intent(this, RequestActivity::class.java)
            newActivityIntent.putExtra("requestId", request.cddeid)
            startActivity(newActivityIntent)
        }
    }
}