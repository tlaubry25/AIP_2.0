package com.authentic.aip.data.remote.dto.notesList


import com.authentic.aip.data.remote.dto.FormErrors
import com.authentic.aip.data.remote.dto.Status
import com.google.gson.annotations.SerializedName

data class NotesListResponseDto(
    @SerializedName("data")
    val `data`: NotesListDto,
    @SerializedName("form_errors")
    val formErrors: FormErrors,
    @SerializedName("status")
    val status: Status
)