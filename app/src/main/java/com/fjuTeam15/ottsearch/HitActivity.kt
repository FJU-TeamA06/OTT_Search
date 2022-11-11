package com.fjuTeam15.ottsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class HitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hit)
        title = "熱門"
        val spinner1 = findViewById<Spinner>(R.id.spinnerHIT)
        val adapter = ArrayAdapter.createFromResource(this, R.array.HIT, android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter
    }
}