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
        val intent = intent
        typeView = intent.getStringExtra("typeRequest")
        loadRequests()
        val rv_listRequest = findViewById<RecyclerView>(R.id.rv_request_list)
        rv_listRequest.layoutManager = LinearLayoutManager(this)
        adapter = RequestListAdapter(this, listOf(), this)
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
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR") }
                it.listRequest!=null ->{
                    Log.d("TLA", "STATE SUCCESS")

                    initview(it.listRequest, typeView)
                }
            }
        }
    }

    private fun initview(listRequest: ListRequest, typeView:String?){
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

    }

    private fun loadRequests(){
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)

        if (sessionId != null && typeView!=null) {
            if(typeView == EnumClass.TypeRequestEnum.ONGOING.toString()){
                listRequestViewModel.listRequest(sessionId, 'A', false, pageNumber)
            }else{
                listRequestViewModel.listRequest(sessionId, 'V', true, pageNumber)
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
            uidEditor?.commit()

            val newActivityIntent = Intent(this, RequestActivity::class.java)
            newActivityIntent.putExtra("requestId", request.cddeid)
            startActivity(newActivityIntent)
        }
    }
}