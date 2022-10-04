package com.authentic.aip.data.remote.api

import com.authentic.aip.data.remote.dto.listAttachments.ListAttachmentsResponseDto
import com.authentic.aip.data.remote.dto.listRequest.ListRequestResponseDto
import com.authentic.aip.data.remote.dto.nbRequest.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.login.LoginResponseDto
import com.authentic.aip.data.remote.dto.notesList.NotesListResponseDto
import com.authentic.aip.data.remote.dto.requestDetail.RequestDetailResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AipApi {

    @POST("login")
    suspend fun loginRequest(@Query("login")login : String, @Query("pwd")pwd : String, @Query("lng")lng:String): LoginResponseDto

    @GET("getNbRequests")
    suspend fun getNbRequest(@Query("uid")uid : String, @Query("orderType")orderType : Char, @Query("histo")histo:Boolean?): GetNbRequestResponseDto

    @GET("listRequests")
    suspend fun listRequests(@Query("uid")uid : String, @Query("orderType")orderType : Char, @Query("histo")histo:Boolean, @Query("numPg")numPg:Int): ListRequestResponseDto

    @GET("viewRequestHead")
    suspend fun viewRequestHead(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("orderType")orderType:Char?): RequestDetailResponseDto

    @POST("validateRequest")
    fun validateRequest(@Query("uid")uid : Long, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): Any

    @POST("denyRequest")
    fun denyRequest(@Query("uid")uid : Long, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): Any

    @POST("explainRequest")
    fun explainRequest(@Query("uid")uid : Long, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): Any

    @GET("listRequestLines")
    fun listRequestLines(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("orderType")orderType:Char, @Query("originalOrder")originalOrder: Boolean, @Query("numPg")numPg:Int): Any

    @GET("listNotes")
    fun listNotes(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("numPg")numPg:Int): NotesListResponseDto

    @GET("getRequestLineChanges")
    fun getRequestLineChanges(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int): Any

    @GET("getText")
    fun getText(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int): Any

    @GET("listAttachements")
    fun listAttachements(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("numPg")numPg:Int): ListAttachmentsResponseDto

    @GET("getAttachement")
    fun getAttachement(@Query("uid")uid : Long, @Query("cddeid")cddeid : String, @Query("deli")deli:Int?, @Query("doct")doct:String, @Query("docName")docName:String): Any

    @GET("listValidators")
    fun listValidators(@Query("uid")uid : String, @Query("cddeid")cddeid : String?, @Query("numPg")numPg:Int): Any

    @PUT("updateValidator")
    fun updateValidator(@Query("uid")uid : Long, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("valr")valr:String, @Query("comment")comment:String): Any
}