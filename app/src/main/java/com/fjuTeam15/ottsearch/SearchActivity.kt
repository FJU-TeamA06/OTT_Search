package com.fjuTeam15.ottsearch

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


class SearchActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var dialog: AlertDialog
    private var requestQueue: RequestQueue? = null
    private val ACTIVITY_TAG = "LogJSON"
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
        val editText: EditText = findViewById(R.id.et_Name)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()
        requestQueue = Volley.newRequestQueue(this)

    }
    fun switchToSend(view: View) {
        /*
        val intent = Intent(this, SendActivity::class.java)
        startActivity(intent)
         */
        Toast.makeText(getApplicationContext(), //Context
            "This is my message", // Message to display
            Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
        ).show(); //Finally Show the toast
        jsonParse()

    }
    private fun jsonParse() {
        dialog.show()
        val editText:EditText = findViewById(R.id.et_Name)
        val keyword=editText.text

        val url = "http://140.136.151.129:8088/getkeyword?keyword=$keyword"
        val request = JsonObjectRequest(Request.Method.GET, url, null, {
                response ->try {
            val jsonArray = response.getJSONArray("data")
            val listData = ArrayList<String>()
            data class VD(
                val title: String,
                val platform: String,
                val URL: String
            )
            for (i in 0 until jsonArray.length()) {
                val DataJson = jsonArray.getJSONObject(i)
                val Title = DataJson.getString("Title")
                val Platform = DataJson.getString("Platform")
                val URL = DataJson.getString("URL")
                //textView.append("Title: $firstName\nPlatform: $age\nURL: $mail\n---------\n")
                listData.add(Title)
                Log.d(ACTIVITY_TAG, Title+"|"+Platform+"|"+URL)
                //textView.setTextIsSelectable(true);
                //textView.movementMethod = ScrollingMovementMethod()
                //textView.setMovementMethod(LinkMovementMethod.getInstance());
            }
            runOnUiThread {
                dialog.dismiss()

            }
            dialog.dismiss()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, {  })
        request.setRetryPolicy(
            DefaultRetryPolicy(
                20000,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue?.add(request)
    }
}