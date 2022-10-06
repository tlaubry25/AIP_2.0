package com.authentic.aip.data.remote.api

import com.authentic.aip.data.remote.dto.genericResponse.GenericResponseDto
import com.authentic.aip.data.remote.dto.listAttachments.ListAttachmentsResponseDto
import com.authentic.aip.data.remote.dto.listRequest.ListRequestResponseDto
import com.authentic.aip.data.remote.dto.nbRequest.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.login.LoginResponseDto
import com.authentic.aip.data.remote.dto.notesList.NotesListResponseDto
import com.authentic.aip.data.remote.dto.request.RequestResponseDto
import com.authentic.aip.data.remote.dto.requestDetail.RequestDetailResponseDto
import com.authentic.aip.data.remote.dto.requestGetText.RequestGetTextResponseDto
import com.authentic.aip.data.remote.dto.validatorList.ValidatorListResponseDto
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
    suspend fun viewRequestHead(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("orderType")orderType:Char?): RequestResponseDto

    @GET("listRequestLines")
    suspend fun listRequestLines(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("orderType")orderType:Char, @Query("originalOrder")originalOrder: Boolean?, @Query("numPg")numPg:Int): RequestDetailResponseDto

    @GET("listNotes")
    suspend fun listNotes(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("numPg")numPg:Int): NotesListResponseDto

    @GET("getRequestLineChanges")
    suspend fun getRequestLineChanges(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int): Any

    @GET("getText")
    suspend fun getText(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int): RequestGetTextResponseDto

    @GET("listAttachments")
    suspend fun listAttachements(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("numPg")numPg:Int): ListAttachmentsResponseDto

    @GET("getAttachment")
    suspend fun getAttachement(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int?, @Query("doct")doct:String, @Query("docName")docName:String): Any

    @GET("listValidators")
    suspend fun listValidators(@Query("uid")uid : String, @Query("cddeid")cddeid : String?, @Query("numPg")numPg:Int): ValidatorListResponseDto

    @POST("validateRequest")
    suspend fun validateRequest(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): GenericResponseDto

    @POST("denyRequest")
    suspend fun denyRequest(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): GenericResponseDto

    @POST("explainRequest")
    suspend fun explainRequest(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): GenericResponseDto

    @PUT("updateValidator")
    suspend fun updateValidator(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("valr")valr:String, @Query("comment")comment:String): GenericResponseDto

    @GET("verifyUrl")
    suspend fun verifyUrl(): GenericResponseDto

    @POST("registerDevice")
    fun registerDevice(@Query("uid")uid : String, @Query("type")type : Char, @Query("did")did:String): Any
}