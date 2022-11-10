package com.fjuTeam15.ottsearch

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title = "查詢"
        val spinner1 = findViewById<Spinner>(R.id.spinnerOTTs)
        val adapter = ArrayAdapter.createFromResource(this, R.array.OTTs, android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter

        // Initialize data.
        val myDataset = Datalist().itemList()

        val recyclerView = findViewById<RecyclerView>(R.id.OTT_list1)
        recyclerView.adapter = Adapter1(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

    }
}