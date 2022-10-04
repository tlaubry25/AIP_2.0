package com.authentic.aip.data.remote.dto.notesList


import com.authentic.aip.domain.model.Notes
import com.google.gson.annotations.SerializedName

data class NotesDto(
    @SerializedName("datetime")
    val datetime: Long,
    @SerializedName("text")
    val text: String,
    @SerializedName("userName")
    val userName: String
)

fun NotesDto.toNotes():Notes{
    return Notes(datetime = datetime, text = text, userName = userName)
}