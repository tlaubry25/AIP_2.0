package com.authentic.aip.presentation.notesList.component

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.authentic.aip.R
import com.authentic.aip.domain.model.Notes
import com.authentic.aip.domain.model.POs
import java.text.SimpleDateFormat

class NotesListAdapter : RecyclerView.Adapter<NotesListAdapter.MyViewHolder>{
    private var context: Context? = null
    private var notesList: List<Notes>? = null

    constructor(contextInput: Context, notesListInput:List<Notes>){
        context = contextInput
        notesList = notesListInput
    }
    fun setNotesList(listNotes : List<Notes>){
        var mutableNotesList : MutableList<Notes> = mutableListOf()
        notesList?.let { mutableNotesList.addAll(it) }
        listNotes.let { mutableNotesList.addAll(it) }
        notesList = mutableNotesList
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNoteUsername: TextView
        var tvDate: TextView
        var tvNoteText: TextView

        init {
            tvNoteUsername = itemView.findViewById<View>(R.id.tv_note_username) as TextView
            tvDate = itemView.findViewById<View>(R.id.tv_note_date) as TextView
            tvNoteText = itemView.findViewById<View>(R.id.tv_note_text) as TextView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvNoteUsername.setText(this.notesList?.get(position)?.userName)
        //holder.tvRequester.setText(this.requestList?.get(position)?.rqstName)
        var dateLong = this.notesList?.get(position)?.datetime
        val dateFormat = SimpleDateFormat("- dd/MM/yyyy à HH:mm")
        holder.tvDate.text = dateFormat.format(dateLong)
        holder.tvNoteText.setText(this.notesList?.get(position)?.text)

    }

    override fun getItemCount(): Int {
        return if (notesList != null) {
            notesList!!.size
        } else 0
    }
}