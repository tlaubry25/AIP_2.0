package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.Notes
import com.authentic.aip.domain.model.NotesList
import com.authentic.aip.domain.model.POs
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.listRequest.component.RequestListAdapter
import com.authentic.aip.presentation.notesList.NotesListViewModel
import com.authentic.aip.presentation.notesList.component.NotesListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListActivity:AppCompatActivity() {
    private val notesListViewModel: NotesListViewModel by viewModels()
    private lateinit var adapter : NotesListAdapter
    private var pageNumber = 1
    var deli : Int?=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_list)
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
        loadRequests()

        val rv_notesList = findViewById<RecyclerView>(R.id.rv_notes_list)
        rv_notesList.layoutManager = LinearLayoutManager(this)
        adapter = NotesListAdapter(this, listOf())
        rv_notesList.adapter = adapter
        rv_notesList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        notesListViewModel.notesListLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.notesList!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE SUCCESS")
                    if(it.notesList.listNotes!=null){
                        initview(it.notesList)
                    }
                }
            }
        }

        rv_notesList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1)){
                    callPagination()
                }
            }
        })
    }

    fun initview(listNotes : NotesList){
        var listNotesToFill : MutableList<Notes> = mutableListOf()
        if(listNotes.listNotes!=null){
            for(note in listNotes.listNotes!!){
                if(note!=null){
                    listNotesToFill.add(note)
                }
            }
        }
        adapter.setNotesList(listNotesToFill)
        adapter.notifyDataSetChanged()
    }
    private fun loadRequests(){
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val requestId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if (sessionId != null && requestId != null) {
            if(deli!=null){
                notesListViewModel.notesList(sessionId, requestId, deli!!, pageNumber)
            }else{
                notesListViewModel.notesList(sessionId, requestId, 0, pageNumber)
            }

        }
    }

    private fun callPagination(){
        pageNumber++
        loadRequests()
    }
}