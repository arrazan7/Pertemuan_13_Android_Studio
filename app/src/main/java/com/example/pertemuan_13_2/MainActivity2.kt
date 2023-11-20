package com.example.pertemuan_13_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pertemuan_13_2.database.NoteDao
import com.example.pertemuan_13_2.database.NoteRoomDatabase
import com.example.pertemuan_13_2.database.Notes
import com.example.pertemuan_13_2.databinding.ActivityMain2Binding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var mNoteDao: NoteDao
    private lateinit var executorService: ExecutorService
    private var updateId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNoteDao = db!!.noteDao()!!

        with(binding) {
            btnInsert.setOnClickListener(View.OnClickListener {
                insert(
                    Notes(
                        id = updateId,
                        title = txtTitle.text.toString(),
                        description = txtDesc.text.toString(),
                        date = txtDate.text.toString()
                    )
                )
                startActivity(Intent(this@MainActivity2, MainActivity::class.java))
            })
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
}