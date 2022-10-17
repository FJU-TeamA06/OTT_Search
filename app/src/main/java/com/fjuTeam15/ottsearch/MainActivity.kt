package com.fjuTeam15.ottsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var buttonSearch: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonSearch = findViewById(R.id.buttonSearch)
    }
    fun SwitchToSearch(view: View){
        val intent = Intent(this, SearchActivity::class.java )
        startActivity(intent)
    }
}
