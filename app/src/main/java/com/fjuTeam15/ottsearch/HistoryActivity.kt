package com.fjuTeam15.ottsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        title = "紀錄"

        val myDataset2 = Datalist2().hitList()

        val recyclerView = findViewById<RecyclerView>(R.id.r21)
        recyclerView.adapter = Adapter2(this, myDataset2)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }
}