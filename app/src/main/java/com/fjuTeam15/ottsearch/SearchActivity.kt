package com.fjuTeam15.ottsearch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
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

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(this@SearchActivity, spinner1.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        // Initialize data.
        val myDataset = Datalist().itemList()

        val recyclerView = findViewById<RecyclerView>(R.id.OTT_list2)
        recyclerView.adapter = Adapter1(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

    }
    fun switchToSend(view: View) {
        val intent = Intent(this, SendActivity::class.java)
        startActivity(intent)
    }
}