package com.fjuTeam15.ottsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class HitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hit)
        title = "熱門"

        val myDataset1 = Datalist().itemList()

        val recyclerView = findViewById<RecyclerView>(R.id.rr1)
        recyclerView.adapter = Adapter1(this, myDataset1)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }
}