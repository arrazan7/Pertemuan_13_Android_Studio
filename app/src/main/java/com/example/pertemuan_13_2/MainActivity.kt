package com.example.pertemuan_13_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan_13_2.database.NoteDao
import com.example.pertemuan_13_2.database.NoteRoomDatabase
import com.example.pertemuan_13_2.database.Notes
import com.example.pertemuan_13_2.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mNoteDao: NoteDao
    private lateinit var executorService: ExecutorService
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var itemAdapter: NoteAdapter
    private val listViewData = ArrayList<Notes>()
    private var updateId: Int = 0

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_DATE = "extra_date"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNoteDao = db!!.noteDao()!!

        manager = LinearLayoutManager(this@MainActivity)

        itemAdapter = NoteAdapter(this, listViewData) { item ->
            // Handle item click event
            // Misalnya, buka detail catatan atau lakukan tindakan lain
            updateId = item.id
            val IntentToThirdActivity = Intent(this, MainActivity3::class.java)
                .apply {
                    putExtra(EXTRA_TITLE, item.title)
                    putExtra(EXTRA_DESC, item.description)
                    putExtra(EXTRA_DATE, item.date)
                    putExtra(EXTRA_ID, item.id)
                }
            startActivity(IntentToThirdActivity)
        }

        with(binding) {
            btnAdd.setOnClickListener {
                val IntentToSecondActivity = Intent(this@MainActivity, MainActivity2::class.java)
                startActivity(IntentToSecondActivity)
            }

            listView.layoutManager = LinearLayoutManager(this@MainActivity)
            listView.adapter = itemAdapter
        }
    }

    private fun getNotes() {
        mNoteDao.allNotes.observe(this) { notes ->
            listViewData.clear()
            listViewData.addAll(notes)
            itemAdapter.notifyDataSetChanged()

            Log.d("MainActivity", "Number of notes: ${notes.size}")
        }
    }

    private fun insert(note: Notes) {
        executorService.execute {mNoteDao.insert(note)}
    }
    private fun update(note: Notes) {
        executorService.execute {mNoteDao.update(note)}
    }
    private fun delete(note: Notes) {
        executorService.execute {mNoteDao.delete(note)}
    }

    override fun onResume() {
        super.onResume()
        getNotes()
    }
}