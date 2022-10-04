package com.authentic.aip.presentation.listAttachments.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.Attachments
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListAttachmentsAdapter : RecyclerView.Adapter<ListAttachmentsAdapter.MyViewHolder>{
    private var context: Context? = null
    private var attachmentsList: List<Attachments>? = null
    private var clickListener: ItemClickListener? = null

    constructor(contextInput: Context, requestListInput:List<Attachments>, clickListenerInput : ItemClickListener){
        context = contextInput
        attachmentsList = requestListInput
        clickListener = clickListenerInput
    }
    fun setAttachmentsList(listRequest : List<Attachments>){
        var mutableRequestList : MutableList<Attachments> = mutableListOf()
        attachmentsList?.let { mutableRequestList.addAll(it) }
        listRequest?.let { mutableRequestList.addAll(it) }
        attachmentsList = mutableRequestList
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var ivAttachments: ImageView


        init {
            tvTitle = itemView.findViewById<View>(R.id.tv_request_title) as TextView
            ivAttachments = itemView.findViewById<View>(R.id.iv_status) as ImageView

        }
    }

    interface ItemClickListener {
        fun onAttachmentClick(attachment: Attachments?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.attachment_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.setText(this.attachmentsList?.get(position)?.docName)
        var drawableTypeAttachments : Drawable? = null
        when(this.attachmentsList?.get(position)?.type){
            EnumClass.TypeAttachmentEnum.PDF.toString()->{
                drawableTypeAttachments = ContextCompat.getDrawable(context!!, R.drawable.pdf)
            }
            EnumClass.TypeAttachmentEnum.EXCEL.toString()->{
                drawableTypeAttachments = ContextCompat.getDrawable(context!!, R.drawable.excel)
            }
            EnumClass.TypeAttachmentEnum.WORD.toString()->{
                drawableTypeAttachments = ContextCompat.getDrawable(context!!, R.drawable.word)
            }
            EnumClass.TypeAttachmentEnum.ZIP.toString()->{
                drawableTypeAttachments = ContextCompat.getDrawable(context!!, R.drawable.zip)
            }
            EnumClass.TypeAttachmentEnum.TXT.toString()->{
                drawableTypeAttachments = ContextCompat.getDrawable(context!!, R.drawable.txt)
            }

        }

        holder.itemView.setOnClickListener { clickListener?.onAttachmentClick(attachmentsList?.get(position)) }
        if(drawableTypeAttachments!=null){
            Glide.with(context!!)
                .load(drawableTypeAttachments)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.ivAttachments)
        }
    }

    override fun getItemCount(): Int {
        return if (attachmentsList != null) {
            attachmentsList!!.size
        } else 0
    }
}