package com.authentic.aip.data.remote.api

import com.authentic.aip.data.remote.dto.genericResponse.GenericResponseDto
import com.authentic.aip.data.remote.dto.getAttachment.GetAttachmentResponseDto
import com.authentic.aip.data.remote.dto.listAttachments.ListAttachmentsResponseDto
import com.authentic.aip.data.remote.dto.listRequest.ListRequestResponseDto
import com.authentic.aip.data.remote.dto.nbRequest.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.login.LoginResponseDto
import com.authentic.aip.data.remote.dto.notesList.NotesListResponseDto
import com.authentic.aip.data.remote.dto.request.RequestResponseDto
import com.authentic.aip.data.remote.dto.requestDetail.RequestDetailResponseDto
import com.authentic.aip.data.remote.dto.requestDetailModification.RequestDetailModificationDto
import com.authentic.aip.data.remote.dto.requestDetailModification.RequestDetailModificationResponseDto
import com.authentic.aip.data.remote.dto.requestGetText.RequestGetTextResponseDto
import com.authentic.aip.data.remote.dto.validatorList.ValidatorListResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AipApi {

    @POST("/aip/rs/api/login")
    suspend fun loginRequest(@Query("login")login : String, @Query("pwd")pwd : String, @Query("lng")lng:String): LoginResponseDto

    @GET("/aip/rs/api/getNbRequests")
    suspend fun getNbRequest(@Query("uid")uid : String, @Query("orderType")orderType : Char, @Query("histo")histo:Boolean?): GetNbRequestResponseDto

    @GET("/aip/rs/api/listRequests")
    suspend fun listRequests(@Query("uid")uid : String, @Query("orderType")orderType : Char, @Query("histo")histo:Int, @Query("numPg")numPg:Int): ListRequestResponseDto

    @GET("/aip/rs/api/viewRequestHead")
    suspend fun viewRequestHead(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("orderType")orderType:Char?): RequestResponseDto

    @GET("/aip/rs/api/listRequestLines")
    suspend fun listRequestLines(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("orderType")orderType:Char, @Query("originalOrder")originalOrder: Int?, @Query("numPg")numPg:Int): RequestDetailResponseDto

    @GET("/aip/rs/api/listNotes")
    suspend fun listNotes(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("numPg")numPg:Int): NotesListResponseDto

    @GET("/aip/rs/api/getRequestLineChanges")
    suspend fun getRequestLineChanges(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int): RequestDetailModificationResponseDto

    @GET("/aip/rs/api/getText")
    suspend fun getText(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int): RequestGetTextResponseDto

    @GET("/aip/rs/api/listAttachments")
    suspend fun listAttachments(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("numPg")numPg:Int): ListAttachmentsResponseDto

    @GET("/aip/rs/api/getAttachment")
    suspend fun getAttachement(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int?, @Query("doct")doct:String, @Query("docName")docName:String): GetAttachmentResponseDto

    @GET("/aip/rs/api/listValidators")
    suspend fun listValidators(@Query("uid")uid : String, @Query("cddeid")cddeid : String?, @Query("numPg")numPg:Int): ValidatorListResponseDto

    @POST("/aip/rs/api/validateRequest")
    suspend fun validateRequest(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): GenericResponseDto

    @POST("/aip/rs/api/denyRequest")
    suspend fun denyRequest(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): GenericResponseDto

    @POST("/aip/rs/api/explainRequest")
    suspend fun explainRequest(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("orderType")orderType:Char, @Query("comment")comment:String): GenericResponseDto

    @PUT("/aip/rs/api/updateValidator")
    suspend fun updateValidator(@Query("uid")uid : String, @Query("cddeid")cddeid : String, @Query("deli")deli:Int, @Query("valr")valr:String, @Query("comment")comment:String): GenericResponseDto

    @GET("/aip/rs/api/verifyUrl")
    suspend fun verifyUrl(): GenericResponseDto

    @POST("/aip/rs/api/registerDevice")
    suspend fun registerDevice(@Query("uid")uid : String, @Query("type")type : Char, @Query("did")did:String): GenericResponseDto
}