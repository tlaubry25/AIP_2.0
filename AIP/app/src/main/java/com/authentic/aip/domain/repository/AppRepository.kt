package com.authentic.aip.domain.repository

import com.authentic.aip.data.remote.dto.listAttachments.ListAttachmentsResponseDto
import com.authentic.aip.data.remote.dto.listRequest.ListRequestResponseDto
import com.authentic.aip.data.remote.dto.nbRequest.GetNbRequestResponseDto
import com.authentic.aip.data.remote.dto.login.LoginResponseDto
import com.authentic.aip.data.remote.dto.notesList.NotesListResponseDto
import com.authentic.aip.data.remote.dto.requestDetail.RequestDetailResponseDto

interface AppRepository {

    suspend fun getLogin(login:String, password : String): LoginResponseDto
    suspend fun getNbRequest(uid:String, orderType : Char, histo:Boolean?): GetNbRequestResponseDto
    suspend fun listRequests(uid : String, orderType : Char, histo:Boolean, numPg:Int): ListRequestResponseDto
    suspend fun viewRequestHead(uid : String, cddeid : String, orderType:Char?): RequestDetailResponseDto

/*    suspend fun validateRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String): Any
    suspend fun denyRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String): Any
    suspend fun explainRequest(uid : String, cddeid : String, deli:Int, orderType:Char, comment:String): Any*/

    suspend fun listNotes(uid : String, cddeid : String, deli:Int, numPg:Int): NotesListResponseDto
    /*suspend fun getText(uid : String, cddeid : String, deli:Int): Any*/

    suspend fun listAttachements(uid : String, cddeid : String, deli:Int, numPg:Int): ListAttachmentsResponseDto
    /*
    suspend fun getAttachement(uid : Long, cddeid : String, deli:Int?, doct:String, docName:String): Any

    suspend fun listValidators(uid : String, cddeid : String?, numPg:Int): Any
    suspend fun updateValidator(uid : Long, cddeid : String, deli:Int, valr:String, comment:String): Any*/

}