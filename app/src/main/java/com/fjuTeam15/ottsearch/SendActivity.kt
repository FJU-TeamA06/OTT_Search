package com.fjuTeam15.ottsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjuTeam15.ottsearch.R.id.OTT_list2

class SendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        title = "result"

        val myDataset = Datalist().itemList()

        val recyclerView = findViewById<RecyclerView>(OTT_list2)
        recyclerView.adapter = Adapter1(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

    }
}