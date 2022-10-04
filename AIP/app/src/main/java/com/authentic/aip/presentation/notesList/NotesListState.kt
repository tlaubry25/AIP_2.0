package com.authentic.aip.presentation.notesList

import com.authentic.aip.domain.model.NotesList

class NotesListState(
    val isLoading : Boolean = false,
    val error : String = "",
    val notesList : NotesList? = null
)