package com.authentic.aip.data.remote.dto.notesList


import com.authentic.aip.data.remote.dto.FormErrorsDto
import com.authentic.aip.data.remote.dto.StatusDto
import com.google.gson.annotations.SerializedName

data class NotesListResponseDto(
    @SerializedName("data")
    val `data`: NotesListDto,
    @SerializedName("form_errors")
    val formErrorsDto: FormErrorsDto,
    @SerializedName("status")
    val statusDto: StatusDto
)