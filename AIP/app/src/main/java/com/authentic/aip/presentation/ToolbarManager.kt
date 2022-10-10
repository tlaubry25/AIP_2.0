package com.authentic.aip.presentation

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass

object ToolbarManager {

    fun setTitleText(appCompatActivity: AppCompatActivity, titleText : String){
        val tvTitleToolbar = appCompatActivity.findViewById<TextView>(R.id.tv_toolbar_center_title)
        tvTitleToolbar.text = titleText
    }

    fun setDrawable(appCompatActivity: AppCompatActivity, idDrawable : Int?){
        if(idDrawable!=null){
            val ivTitleToolbar = appCompatActivity.findViewById<ImageView>(R.id.iv_toolbar_center_icon)
            ivTitleToolbar.background = ContextCompat.getDrawable(appCompatActivity,idDrawable)
        }
    }
    fun setBackpress(appCompatActivity: AppCompatActivity){
        val clBackpressToolbar = appCompatActivity.findViewById<ConstraintLayout>(R.id.cl_content_toolbar)
        clBackpressToolbar.setOnClickListener {
            appCompatActivity.onBackPressed()
        }
    }

    fun setDrawableByCodeStatus(appCompatActivity: AppCompatActivity, statusCode : String?){
        val ivTitleToolbar = appCompatActivity.findViewById<ImageView>(R.id.iv_toolbar_center_icon)
        if(statusCode.isNullOrEmpty()){
            ivTitleToolbar.visibility = View.GONE
        }else{
            ivTitleToolbar.visibility = View.VISIBLE
            val idDrawable = getIdDrawableByStatusCode(statusCode)
            if(idDrawable!=null){
                ivTitleToolbar.background = ContextCompat.getDrawable(appCompatActivity,idDrawable)
            }
        }
    }

    private fun getIdDrawableByStatusCode(statusCode: String):Int?{
        val idIconDrawable : Int?
        when(statusCode){
            EnumClass.StatusRequestEnum.EN_CREATION.statusCode,
            EnumClass.StatusRequestEnum.EN_COURS_VALIDATION.statusCode,
            EnumClass.StatusRequestEnum.DMOA_EN_CREATION.statusCode,
            EnumClass.StatusRequestEnum.VALIDATION_EN_COURS.statusCode ->{
                idIconDrawable = R.drawable.approuved_white
            }
            EnumClass.StatusRequestEnum.VALIDE_COMPLET.statusCode,
            EnumClass.StatusRequestEnum.EN_COURS.statusCode,
            EnumClass.StatusRequestEnum.APPROUVE_EN_ATTENTE.statusCode,
            EnumClass.StatusRequestEnum.VALIDEE.statusCode,
            EnumClass.StatusRequestEnum.COMPLETE_APPROB_REQUIS.statusCode,
            EnumClass.StatusRequestEnum.EN_COURS_APPROB.statusCode->{
                idIconDrawable = R.drawable.thumb_blanc
            }
            else->{idIconDrawable = null}
        }
        return idIconDrawable
    }
}