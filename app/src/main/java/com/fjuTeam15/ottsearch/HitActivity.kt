package com.fjuTeam15.ottsearch

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class HitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hit)
        title = "熱門"
        val spinner1 = findViewById<Spinner>(R.id.spinnerHIT)
        val adapter = ArrayAdapter.createFromResource(this, R.array.HIT, android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(this@HitActivity, spinner1.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        val myDataset2 = Datalist2().hitList()

        val recyclerView = findViewById<RecyclerView>(R.id.rr1)
        recyclerView.adapter = Adapter2(this, myDataset2)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }
}