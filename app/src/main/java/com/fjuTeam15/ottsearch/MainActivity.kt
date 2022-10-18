package com.fjuTeam15.ottsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var buttonSearch: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonSearch = findViewById(R.id.buttonSearch)
        title = "OTT影音查詢"
    }
    fun switchToSearch(view: View){
        val intent = Intent(this, SearchActivity::class.java )
        startActivity(intent)
    }
    fun switchToHit(view: View){
        val intent = Intent(this, HitActivity::class.java )
        startActivity(intent)
    }
    fun switchToHistory(view: View){
        val intent = Intent(this, HistoryActivity::class.java )
        startActivity(intent)
    }
    fun switchToList(view: View){
        val intent = Intent(this, ListActivity::class.java )
        startActivity(intent)
    }
}
