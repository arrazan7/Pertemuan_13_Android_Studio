package com.example.pertemuan_13_2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan_13_2.database.Notes
import com.example.pertemuan_13_2.databinding.ListNoteBinding

class NoteAdapter (private val context: Context, private val listData: List<Notes>, private val onClickData: (Notes) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.ItemDataViewHolder>() {

    inner class ItemDataViewHolder(private val binding: ListNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Notes) {
            with(binding) {
                // Lakukan binding data ke tampilan
                noteTitle.text = data.title
                noteDesc.text = data.description
                noteDate.text = data.date

                // Atur listener untuk onClick
                itemView.setOnClickListener {
                    onClickData(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDataViewHolder {
        val binding = ListNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemDataViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}