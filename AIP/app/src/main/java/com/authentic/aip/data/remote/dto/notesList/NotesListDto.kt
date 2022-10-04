package com.authentic.aip.data.remote.dto.notesList

import com.authentic.aip.data.remote.dto.PageDataDto
import com.authentic.aip.data.remote.dto.toPageData
import com.authentic.aip.domain.model.Notes
import com.authentic.aip.domain.model.NotesList
import com.google.gson.annotations.SerializedName

data class NotesListDto(
    @SerializedName("listNotes")
    val listNotes: List<NotesDto?>?=null,
    @SerializedName("pageData")
    val pageData: PageDataDto?
)

fun NotesListDto.toNotesList():NotesList{
    val notesList : MutableList<Notes> = mutableListOf()
    if(!listNotes.isNullOrEmpty()){
        for(note in listNotes){
            val noteConverted = note?.toNotes()
            if(noteConverted!=null){
                notesList.add(noteConverted)
            }
        }
    }
    return NotesList(listNotes = notesList, pageData = pageData?.toPageData())
}