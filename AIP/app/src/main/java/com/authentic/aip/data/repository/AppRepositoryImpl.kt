package com.authentic.aip.data.repository

import com.authentic.aip.data.remote.api.AipApi
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
import com.authentic.aip.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private var appApi : AipApi):AppRepository{

    override suspend fun getLogin(login:String, password : String): LoginResponseDto {
        return appApi.loginRequest(login, password, "fr")
    }

    override suspend fun getNbRequest(uid:String, orderType : Char, histo:Boolean?): GetNbRequestResponseDto {
        return appApi.getNbRequest(uid, orderType, histo)
    }

    override suspend fun listRequests(uid: String, orderType: Char, histo: Boolean, numPg: Int): ListRequestResponseDto {
        return appApi.listRequests(uid, orderType, histo, numPg)
    }

    override suspend fun viewRequestHead(uid: String, cddeid: String, orderType: Char?): RequestResponseDto {
        return appApi.viewRequestHead(uid, cddeid, orderType)
    }

    override suspend fun listNotes(uid: String, cddeid: String, deli: Int, numPg: Int): NotesListResponseDto {
        return appApi.listNotes(uid, cddeid, deli, numPg)
    }

    override suspend fun getText(uid: String, cddeid: String, deli: Int): RequestGetTextResponseDto {
        return appApi.getText(uid, cddeid, deli)
    }

    override suspend fun listRequestLines(uid: String, cddeid: String, orderType: Char, originalOrder: Boolean?, numPg: Int): RequestDetailResponseDto {
        return appApi.listRequestLines(uid, cddeid, orderType, originalOrder, numPg)
    }

    override suspend fun listAttachements(uid: String, cddeid: String, deli: Int, numPg: Int): ListAttachmentsResponseDto {
        return appApi.listAttachements(uid, cddeid, deli, numPg)
    }

    override suspend fun listValidators(uid: String, cddeid: String?, numPg: Int): ValidatorListResponseDto {
        return appApi.listValidators(uid, cddeid, numPg)
    }

    override suspend fun validateRequest(uid: String, cddeid: String, deli: Int, orderType: Char, comment: String): GenericResponseDto {
        return appApi.validateRequest(uid, cddeid, deli, orderType, comment)
    }

    override suspend fun denyRequest(uid: String, cddeid: String, deli: Int, orderType: Char, comment: String): GenericResponseDto {
        return appApi.denyRequest(uid, cddeid, deli, orderType, comment)
    }

    override suspend fun explainRequest(uid: String, cddeid: String, deli: Int, orderType: Char, comment: String): GenericResponseDto {
        return appApi.explainRequest(uid, cddeid, deli, orderType, comment)
    }

    override suspend fun updateValidator(uid : String, cddeid : String, deli:Int, valr:String, comment:String): GenericResponseDto {
        return appApi.updateValidator(uid, cddeid, deli, valr, comment)
    }

    override suspend fun getAttachement(uid: String, cddeid: String, deli: Int?, doct: String,docName: String): Any {
        return appApi.getAttachement(uid, cddeid, deli, doct, docName)
    }
}