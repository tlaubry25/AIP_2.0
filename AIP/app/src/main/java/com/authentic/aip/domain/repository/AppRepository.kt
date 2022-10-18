package com.authentic.aip.domain.repository

import com.authentic.aip.data.remote.dto.genericResponse.GenericResponseDto
import com.authentic.aip.data.remote.dto.getAttachment.GetAttachmentResponseDto
import com.authentic.aip.data.remote.dto.listAttachments.ListAttachmentsResponseDto
import com.authentic.aip.data.remote.dto.listRequest.ListRequestResponseDto
import com.authentic.aip.data.remote.dto.nbRequest.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.login.LoginResponseDto
import com.authentic.aip.data.remote.dto.notesList.NotesListResponseDto
import com.authentic.aip.data.remote.dto.request.RequestResponseDto
import com.authentic.aip.data.remote.dto.requestDetail.RequestDetailResponseDto
import com.authentic.aip.data.remote.dto.requestDetailModification.RequestDetailModificationResponseDto
import com.authentic.aip.data.remote.dto.requestGetText.RequestGetTextResponseDto
import com.authentic.aip.data.remote.dto.validatorList.ValidatorListResponseDto
import retrofit2.http.Query


interface AppRepository {

    suspend fun getLogin(login:String, password : String, language : String): LoginResponseDto
    suspend fun verifyUrl(): GenericResponseDto
    suspend fun getNbRequest(uid:String, orderType : Char, histo:Boolean?): GetNbRequestResponseDto
    suspend fun listRequests(uid : String, orderType : Char, histo:Int, numPg:Int): ListRequestResponseDto
    suspend fun viewRequestHead(uid : String, cddeid : String, orderType:Char?): RequestResponseDto
    suspend fun listNotes(uid : String, cddeid : String, deli:Int, numPg:Int): NotesListResponseDto
    suspend fun getText(uid : String, cddeid : String, deli:Int): RequestGetTextResponseDto
    suspend fun listRequestLines(uid : String, cddeid : String, orderType:Char, originalOrder: Int?, numPg:Int): RequestDetailResponseDto
    suspend fun getRequestLineChanges(uid : String, cddeid : String, deli:Int): RequestDetailModificationResponseDto
    suspend fun listAttachments(uid : String, cddeid : String, deli:Int, numPg:Int): ListAttachmentsResponseDto
    suspend fun listValidators(uid : String, cddeid : String?, numPg:Int): ValidatorListResponseDto

    suspend fun validateRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String): GenericResponseDto
    suspend fun denyRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String): GenericResponseDto
    suspend fun explainRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String): GenericResponseDto
    suspend fun updateValidator(uid : String, cddeid : String, deli:Int, valr:String, comment:String): GenericResponseDto

    suspend fun getAttachement(uid : String, cddeid : String, deli:Int?, doct:String, docName:String): GetAttachmentResponseDto

    suspend fun registerDevice(uid : String, type : Char, did:String): GenericResponseDto
}