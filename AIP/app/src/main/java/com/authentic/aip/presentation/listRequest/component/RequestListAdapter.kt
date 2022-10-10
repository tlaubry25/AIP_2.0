package com.authentic.aip.presentation.listRequest.component

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
import com.authentic.aip.domain.model.POs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RequestListAdapter : RecyclerView.Adapter<RequestListAdapter.MyViewHolder>{
    private var context: Context? = null
    private var requestList: List<POs>? = null
    private var clickListener: ItemClickListener? = null
    private var typeview : String?=null

    constructor(contextInput: Context, requestListInput:List<POs>, clickListenerInput : ItemClickListener, typeviewIntput :String?=EnumClass.TypeRequestEnum.ONGOING.toString()){
        context = contextInput
        requestList = requestListInput
        clickListener = clickListenerInput
        typeview = typeviewIntput
    }
    fun setRequestList(listRequest : List<POs>){
        var mutableRequestList : MutableList<POs> = mutableListOf()
        requestList?.let { mutableRequestList.addAll(it) }
        listRequest.let { mutableRequestList.addAll(it) }
        requestList = mutableRequestList
        notifyDataSetChanged()

    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvStatus: TextView
        var tvRequester: TextView
        var tvAmount: TextView
        var iv_status: ImageView
        var iv_update: ImageView

        init {
            tvTitle = itemView.findViewById<View>(R.id.tv_request_title) as TextView
            tvStatus = itemView.findViewById<View>(R.id.tv_status) as TextView
            tvRequester = itemView.findViewById<View>(R.id.tv_requester) as TextView
            tvAmount = itemView.findViewById<View>(R.id.tv_amount) as TextView
            iv_status = itemView.findViewById<View>(R.id.iv_status) as ImageView
            iv_update = itemView.findViewById<View>(R.id.iv_update) as ImageView
        }
    }

    interface ItemClickListener {
        fun onRequestClick(request: POs?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.request_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.setText(this.requestList?.get(position)?.objt)
        holder.tvRequester.setText(this.requestList?.get(position)?.rqstName)
        holder.tvAmount.text = context?.getString(R.string.request_amount_text, this.requestList?.get(position)?.dedAmtLoc.toString())
        //TEST EN FONCTION DE L'AVANCEMENT POUR AFFICHER LE STATUT OU NON
        val idTexte : Int
        var showStatus = true


        when(this.requestList?.get(position)?.dest){
            EnumClass.StatusRequestEnum.ANNULEE.statusCode->{
                idTexte = R.string.request_status_canceled
            }
            EnumClass.StatusRequestEnum.EN_COURS.statusCode->{
                idTexte = R.string.request_status_ongoing
                showStatus = false
            }
            EnumClass.StatusRequestEnum.FERMEE.statusCode->{
                idTexte = R.string.request_status_closed
            }
            EnumClass.StatusRequestEnum.VALIDE_COMPLET.statusCode->{
                idTexte = R.string.request_status_accepted_closed
            }
            EnumClass.StatusRequestEnum.VALIDEE.statusCode->{
                idTexte = R.string.request_status_accepted
            }
            EnumClass.StatusRequestEnum.REFUSEE.statusCode->{
                idTexte = R.string.request_status_denied
            }
            else->{
                idTexte = R.string.request_status_unknown
                showStatus = false
            }

        }
        if(showStatus){
            holder.tvStatus.visibility = View.VISIBLE
        }else{
            holder.tvStatus.visibility = View.GONE
        }
        var drawableUpdate : Drawable? = null
        if(this.requestList?.get(position)?.buov!=null){
            if(this.requestList?.get(position)?.buov!!){
                drawableUpdate = ContextCompat.getDrawable(context!!, R.drawable.warning)
            }
        }

        if(this.requestList?.get(position)?.stby!=null){
            if(this.requestList?.get(position)?.stby!!){
                drawableUpdate = ContextCompat.getDrawable(context!!, R.drawable.chrono)

            }
        }
        val idIconDrawable : Int?
        when(this.requestList?.get(position)?.dest){
            EnumClass.StatusRequestEnum.EN_CREATION.statusCode,
            EnumClass.StatusRequestEnum.EN_COURS_VALIDATION.statusCode,
            EnumClass.StatusRequestEnum.DMOA_EN_CREATION.statusCode,
            EnumClass.StatusRequestEnum.VALIDATION_EN_COURS.statusCode ->{
                idIconDrawable = R.drawable.approuved_black
            }
            EnumClass.StatusRequestEnum.VALIDE_COMPLET.statusCode,
            EnumClass.StatusRequestEnum.EN_COURS.statusCode,
            EnumClass.StatusRequestEnum.APPROUVE_EN_ATTENTE.statusCode,
            EnumClass.StatusRequestEnum.VALIDEE.statusCode,
            EnumClass.StatusRequestEnum.COMPLETE_APPROB_REQUIS.statusCode,
            EnumClass.StatusRequestEnum.EN_COURS_APPROB.statusCode->{
                idIconDrawable = R.drawable.thumb
            }
            else->{idIconDrawable = null}
        }
        var drawableStatus : Drawable? = null
        if(idIconDrawable==null){
            holder.iv_update.visibility = View.INVISIBLE
        }else{
            holder.iv_update.visibility = View.VISIBLE
            drawableStatus = ContextCompat.getDrawable(context!!, idIconDrawable)
        }
        when(typeview){
            EnumClass.TypeRequestEnum.ONGOING.toString()->{
                holder.tvStatus.visibility = View.GONE
            }
            else->{
                holder.tvStatus.visibility = View.VISIBLE
            }
        }
        holder.tvStatus.setText(idTexte)

        holder.itemView.setOnClickListener { clickListener?.onRequestClick(requestList?.get(position)) }

        if(drawableStatus!=null){
            Glide.with(context!!)
                .load(drawableStatus)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.iv_status)

        }

        Glide.with(context!!)
            .load(drawableUpdate)
            .apply(RequestOptions.centerCropTransform())
            .into(holder.iv_update)
    }

    override fun getItemCount(): Int {
        return if (requestList != null) {
            requestList!!.size
        } else 0
    }
}