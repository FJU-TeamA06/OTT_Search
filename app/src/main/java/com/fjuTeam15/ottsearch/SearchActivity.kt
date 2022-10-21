package com.fjuTeam15.ottsearch

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title = "查詢"
        val spinner1 = findViewById<Spinner>(R.id.spinnerOTTs)
        val adapter = ArrayAdapter.createFromResource(this, R.array.OTTs, android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter
    }
}