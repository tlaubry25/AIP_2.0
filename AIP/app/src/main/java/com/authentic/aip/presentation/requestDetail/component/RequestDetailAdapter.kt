package com.authentic.aip.presentation.requestDetail.component

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.authentic.aip.R
import com.authentic.aip.domain.model.DedLine

class RequestDetailAdapter : RecyclerView.Adapter<RequestDetailAdapter.AttachmentHolder>{
    private var context: Context? = null
    private var dedLineList: List<DedLine>? = null
    private var clickListener: ItemClickListener? = null

    constructor(contextInput: Context, requestDetailListInput:List<DedLine>, clickListenerInput : ItemClickListener){
        context = contextInput
        dedLineList = requestDetailListInput
        clickListener = clickListenerInput
    }

    fun setDedLineList(listDedLine : List<DedLine>){
        var mutableAttachmentsList : MutableList<DedLine> = mutableListOf()
        dedLineList?.let { mutableAttachmentsList.addAll(it) }
        listDedLine.let { mutableAttachmentsList.addAll(it) }
        dedLineList = mutableAttachmentsList
        notifyDataSetChanged()
    }


    class AttachmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvTotalAmountData: TextView
        var tvQuantity: TextView
        var tvUnitaryPrice: TextView
        var tvNbNotes : TextView
        var tvNbAttachments : TextView
        var tvText : TextView
        init {
            tvTitle = itemView.findViewById<View>(R.id.tv_request_detail_title) as TextView
            tvTotalAmountData = itemView.findViewById<View>(R.id.tv_request_detail_total_amount_data) as TextView
            tvQuantity = itemView.findViewById<View>(R.id.tv_request_detail_amount_data) as TextView
            tvUnitaryPrice = itemView.findViewById<View>(R.id.tv_request_detail_unitary_price_data) as TextView
            tvNbNotes = itemView.findViewById<View>(R.id.tv_section_notes) as TextView
            tvNbAttachments = itemView.findViewById<View>(R.id.tv_section_attachement) as TextView
            tvText = itemView.findViewById(R.id.tv_section_texte)
        }
        fun bind(dedline : DedLine){
            /*        if(this.dedLineList?.get(position)?.nbNotes!=null){
            holder.tvNbNotes.isClickable = this.dedLineList?.get(position)?.nbNotes!! >0
        }
        if(this.dedLineList?.get(position)?.nbDocs!=null){
            holder.tvNbAttachments.isClickable = this.dedLineList?.get(position)?.nbDocs!! >0
        }
        if(this.dedLineList?.get(position)?.nbDocs!=null){
            holder.tvNbAttachments.isClickable = this.dedLineList?.get(position)?.existText!!
        }*/

/*            tvNbNotes.setOnClickListener {
                if(tvNbNotes.isClickable) clickListener?.onNotesClick(dedline)
            }

            tvNbAttachments.setOnClickListener {
                if(tvNbAttachments.isClickable) clickListener?.onAttachmentClick(dedline)
            }

            tvText.setOnClickListener {
                if(tvText.isClickable) clickListener?.onTextClick(dedline)
            }*/
        }

    }
    interface ItemClickListener {
        fun onNotesClick(dedLine: DedLine?)
        fun onAttachmentClick(dedLine: DedLine?)
        fun onTextClick(dedLine: DedLine?)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.request_detail_item, parent, false)
        return AttachmentHolder(view)
    }

    override fun onBindViewHolder(holder: AttachmentHolder, position: Int) {
        holder.tvTitle.text = this.dedLineList?.get(position)?.itds
        holder.tvTotalAmountData.text = " "+context?.getString(R.string.request_detail_data_price,  this.dedLineList?.get(position)?.lnam.toString())
        holder.tvQuantity.text = " "+this.dedLineList?.get(position)?.ppqt.toString()+" "

        holder.tvUnitaryPrice.text = " "+context?.getString(R.string.request_detail_data_price, this.dedLineList?.get(position)?.pupr.toString())
        holder.tvNbNotes.text = context?.getString(R.string.section_notes, this.dedLineList?.get(position)?.nbNotes)
        holder.tvNbAttachments.text = context?.getString(R.string.section_attachement, this.dedLineList?.get(position)?.nbDocs)

        if(this.dedLineList?.get(position)?.nbNotes!=null){
            if(this.dedLineList?.get(position)?.nbNotes!! >0){
                holder.tvNbNotes.setOnClickListener {
                    clickListener?.onNotesClick(this.dedLineList?.get(position))
                }
            }else{
                holder.tvNbNotes.isClickable = false
                holder.tvNbNotes.setTextColor(context!!.getColor(R.color.grey))
            }
        }
        if(this.dedLineList?.get(position)?.nbDocs!=null){
            if(this.dedLineList?.get(position)?.nbDocs!! >0){
                holder.tvNbAttachments.setOnClickListener {
                    clickListener?.onAttachmentClick(this.dedLineList?.get(position))
                }
            }else{
                holder.tvNbAttachments.setTextColor(context!!.getColor(R.color.grey))
                holder.tvNbAttachments.isClickable = false
            }
        }
        if(this.dedLineList?.get(position)?.existText == true){
            holder.tvText.setOnClickListener {
                clickListener?.onTextClick(this.dedLineList?.get(position))
            }
        }else{
            holder.tvText.setTextColor(context!!.getColor(R.color.grey))
            holder.tvText.isClickable = false
        }

    }

    override fun getItemCount(): Int {
        return if (dedLineList != null) {
            dedLineList!!.size
        } else 0
    }
}