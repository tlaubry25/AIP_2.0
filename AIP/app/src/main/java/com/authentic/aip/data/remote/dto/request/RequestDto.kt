package com.authentic.aip.data.remote.dto.request


import com.authentic.aip.domain.model.Request
import com.google.gson.annotations.SerializedName

data class RequestDto(
    @SerializedName("bfidName")
    val bfidName: String,
    @SerializedName("budgAmtLoc")
    val budgAmtLoc: Double,
    @SerializedName("coce")
    val coce: String,
    @SerializedName("dedAmtLoc")
    val dedAmtLoc: Double,
    @SerializedName("dedAmtPur")
    val dedAmtPur: Double,
    @SerializedName("dwdt")
    val dwdt: Int,
    @SerializedName("engaAmtLoc")
    val engaAmtLoc: Double,
    @SerializedName("faci")
    val faci: String,
    @SerializedName("locCur")
    val locCur: String,
    @SerializedName("nbDocs")
    val nbDocs: Int,
    @SerializedName("nbNotes")
    val nbNotes: Int,
    @SerializedName("objt")
    val objt: String,
    @SerializedName("proj")
    val proj: String,
    @SerializedName("purCur")
    val purCur: String,
    @SerializedName("sunm")
    val sunm: String
)

fun RequestDto.toRequest() = Request(
    bfidName = bfidName,
    budgAmtLoc = budgAmtLoc,
    coce = coce,
    dedAmtLoc = dedAmtLoc,
    dedAmtPur = dedAmtPur,
    dwdt = dwdt,
    engaAmtLoc = engaAmtLoc,
    faci = faci,
    locCur = locCur,
    nbDocs = nbDocs,
    nbNotes = nbNotes,
    objt = objt,
    proj = proj,
    purCur = purCur,
    sunm = sunm
)