package com.authentic.aip.presentation

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.document_viewer)
        this.supportActionBar?.hide()
        ToolbarManager.setBackpress(this)
        val ivTitleToolbar = findViewById<ImageView>(R.id.iv_toolbar_center_icon)
        ivTitleToolbar.visibility = View.GONE
        val intent = intent
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val requestId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        deli = intent.getIntExtra("deli", 0)
        attachment =  intent.getParcelableExtra("attachment")
        if(sessionId!=null && requestId!=null && attachment !=null){
            attachment!!.docName?.let {
                attachmentViewModel.getAttachment(sessionId, requestId, deli, attachment!!.doct.toString(),
                    it
                )
            }
        }
        val toolbarTitle = attachment?.docName
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        attachmentViewModel.attachmentLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.attachmentData!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE SUCCESS")
                    initView(it.attachmentData)
                }
            }
        }
    }

    private fun initView(contentData : String){
        val dataInByteArray = android.util.Base64.decode(contentData, android.util.Base64.DEFAULT)
        var extension:String?=null
        when(attachment?.type){
            EnumClass.TypeAttachmentEnum.TXT.toString().lowercase()->{extension="txt"}
            EnumClass.TypeAttachmentEnum.WORD.toString().lowercase()->{extension="docx"}
            EnumClass.TypeAttachmentEnum.EXCEL.toString().lowercase()->{extension="xlsx"}
            EnumClass.TypeAttachmentEnum.PDF.toString().lowercase()->{extension="pdf"}
        }

        if(extension!=null){
            val fileName = attachment?.docName+"."+extension
            val filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path
            if(!filePath.isNullOrEmpty()){
                var myExternalFile:File = File(getExternalFilesDir(filePath),fileName)
                try {
                    val fileOutPutStream = FileOutputStream(myExternalFile)
                    fileOutPutStream.write(dataInByteArray)
                    fileOutPutStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val webView = findViewById<WebView>(R.id.webv_doc_viewer)
                webView.webViewClient = WebViewClient()
                webView.loadUrl(myExternalFile.path)

            }

        }


    }
}