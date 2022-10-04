package com.authentic.aip.data.remote.dto.requestDetail


import com.authentic.aip.domain.model.RequestDetail
import com.google.gson.annotations.SerializedName

data class RequestDetailDto(
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

fun RequestDetailDto.toRequestDetail() = RequestDetail(
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