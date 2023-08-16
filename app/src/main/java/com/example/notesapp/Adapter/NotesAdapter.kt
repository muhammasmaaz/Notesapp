package com.example.notesapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Models.Note
import com.example.notesapp.R

class NotesAdapter (private val context: Context, val listener:Notesclicklistner):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NotesList=ArrayList<Note>()
    private val fullList=ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false))

    }

    override fun getItemCount(): Int {

        return NotesList.size

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentNote=NotesList[position]
        holder.title.text=currentNote.title
        holder.title.isSelected=true
        holder.Notes_tv.text=currentNote.note
        holder.date.text=currentNote.date
        holder.date.isSelected=true

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomcolor(),null))

        holder.notes_layout.setOnClickListener{
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }
        holder.notes_layout.setOnLongClickListener{
            listener.onLongItemClicked(NotesList[holder.adapterPosition],holder.notes_layout)
            true
        }


    }

    fun updateList(newList:List<Note>){
        fullList.clear()
        fullList.addAll(newList)

        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search:String){

        NotesList.clear()

        for (item in fullList){
            if (item.title?.lowercase()?.contains(search.lowercase())==true||
                item.note?.lowercase()?.contains(search.lowercase())==true){
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()

    }

    fun randomcolor():Int{

        val list =ArrayList<Int>()
        list.add(R.color.a)
        list.add(R.color.b)
        list.add(R.color.c)
        list.add(R.color.d)
        list.add(R.color.e)
        list.add(R.color.f)
        list.add(R.color.g)
        val seed=System.currentTimeMillis().toInt()
        val randomIndex= kotlin.random.Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    inner class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val notes_layout=itemView.findViewById<CardView>(R.id.card_layout)
        val title=itemView.findViewById<TextView>(R.id.tv_titlenotes)
        val Notes_tv=itemView.findViewById<TextView>(R.id.tv_notes)
        val date=itemView.findViewById<TextView>(R.id.tv_datenotes)

    }

    interface Notesclicklistner{
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note,cardView: CardView)
    }
}