package com.authentic.aip.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.Attachments
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.documentViewer.DocumentViewerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class DocumentViewerActivity:AppCompatActivity() {
    private val attachmentViewModel: DocumentViewerViewModel by viewModels()
    private var  deli : Int? = null
    private var attachment : Attachments? = null
    lateinit var myExternalFile:File
    var mimeTypes = arrayOf(
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // .doc & .docx
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",  // .xls & .xlsx
        "text/plain",
        "application/pdf",
        "application/zip"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.document_viewer)
        this.supportActionBar?.hide()
        ToolbarManager.setBackpress(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
        }
        val ivTitleToolbar = findViewById<ImageView>(R.id.iv_toolbar_center_icon)
        ivTitleToolbar.visibility = View.GONE
        val intent = intent
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val requestId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        deli = intent.getIntExtra("deli", 0)
        attachment =  intent.getParcelableExtra("attachment")
        if(sessionId!=null && requestId!=null && attachment !=null){
            attachment!!.docName?.let {
                attachmentViewModel.getAttachment(this, sessionId, requestId, deli, attachment!!.doct.toString(),
                    it
                )
            }
        }
        val toolbarTitle = attachment?.docName
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val loadButton = findViewById<Button>(R.id.button_load)
        attachmentViewModel.attachmentLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING")
                }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.attachmentData!=null ->{
                    progressBar.visibility = View.GONE
                    loadButton.visibility = View.VISIBLE
                    Log.d("TLA", "STATE SUCCESS")
                    initView(it.attachmentData)
                }
            }
        }


        loadButton.setOnClickListener {
            loadFile()
        }
    }

    private fun initView(contentData : String){
        val requestTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        val dataInByteArray = android.util.Base64.decode(contentData, android.util.Base64.DEFAULT)
        var extension:String?=null
        when(attachment?.type){
            EnumClass.TypeAttachmentEnum.TXT.toString().lowercase()->{
                extension = "txt"
            }
            EnumClass.TypeAttachmentEnum.DOCX.toString().lowercase()->{
                extension = "docx"
            }
            EnumClass.TypeAttachmentEnum.XLSX.toString().lowercase()->{
                extension = "xlsx"
            }
            EnumClass.TypeAttachmentEnum.PDF.toString().lowercase()->{
                extension = "pdf"
            }
            EnumClass.TypeAttachmentEnum.ZIP.toString().lowercase()->{
                extension = "zip"
            }
        }

        if(extension!=null){
            var filePath :String
            var requestName:String?=""
            if(requestTitle!=null){
                requestName= "/$requestTitle"
            }
            filePath = "Download"+requestName
            val fileName = attachment?.docName+"."+extension

                myExternalFile = File(getExternalFilesDir(filePath),fileName)
                try {

                    val fileOutPutStream = FileOutputStream(myExternalFile, false)
                    fileOutPutStream.write(dataInByteArray)
                    fileOutPutStream.flush()
                    fileOutPutStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
        }
    }

    private fun loadFile(){

        val uri =  Uri.parse(myExternalFile.absolutePath+"/"+attachment?.docName)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            setDataAndType(uri,type)
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        startActivity(intent)
    }
}