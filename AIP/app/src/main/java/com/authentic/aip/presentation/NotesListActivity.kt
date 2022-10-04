package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
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
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_list)
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val requestId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if (sessionId != null && requestId != null) {
            notesListViewModel.requestDetail(sessionId, requestId, 0, 1)
        }
        val rv_notesList = findViewById<RecyclerView>(R.id.rv_notes_list)
        rv_notesList.layoutManager = LinearLayoutManager(this)
        adapter = NotesListAdapter(this, listOf())
        rv_notesList.adapter = adapter
        rv_notesList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))

        notesListViewModel.notesListLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR") }
                it.notesList!=null ->{
                    Log.d("TLA", "STATE SUCCESS")
                    if(it.notesList.listNotes!=null){
                        initview(it.notesList)
                    }
                }
            }
        }
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
}