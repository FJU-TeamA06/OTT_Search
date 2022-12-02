package com.fjuTeam15.ottsearch

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.LogPrinter
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import okhttp3.*
import android.util.Log.d as logD



class SearchActivity : AppCompatActivity() {


    lateinit var listView_details: ListView

    private lateinit var dialog: AlertDialog
    private var requestQueue: RequestQueue? = null
    private val ACTIVITY_TAG = "LogJSON"


    var arrayList_details:ArrayList<Model> = ArrayList();
    //OkHttpClient creates connection pool between client and server
    val client = OkHttpClient()

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
    }
    fun switchToSend(view: View) {

        val editText:EditText = findViewById(R.id.et_Name)
        val keyword=editText.text
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(getApplicationContext(), //Context
                "搜尋不得為空", // Message to display
                Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
            ).show(); //Finally Show the toast
        }
        else
        {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setCancelable(false) // if you want user to wait for some process to finish,
            builder.setView(R.layout.layout_loading_dialog)
            dialog = builder.create()
            requestQueue = Volley.newRequestQueue(this)

            //jsonParse()
            //listOf(logD(ACTIVITY_TAG, jsonParse().toString()))
            listView_details = findViewById<ListView>(R.id.userlist) as ListView
            jsonParse()
        }

    }

    private fun jsonParse() {
        dialog.show()
        val editText:EditText = findViewById(R.id.et_Name)
        val listView:ListView = findViewById(R.id.userlist)
        val keyword=editText.text

        val url = "http://140.136.151.129:8088/getkeyword?keyword=$keyword"
        val request = JsonObjectRequest(Request.Method.GET, url, null, {
                response ->try {
            val jsonArray = response.getJSONArray("data")
            val listData = ArrayList<String>()

            data class VD(val title: String, val platform: String, val URL: String)

            for (i in 0 until jsonArray.length()) {
                val DataJson = jsonArray.getJSONObject(i)
                var model:Model= Model();
                model.title=DataJson.getString("Title")
                model.platform=DataJson.getString("Platform")
                model.url=DataJson.getString("URL")
                arrayList_details.add(model)
                //textView.append("Title: $firstName\nPlatform: $age\nURL: $mail\n---------\n")
                listData.add(model.toString())

                logD(ACTIVITY_TAG, model.title+"|"+model.platform+"|"+model.url)
                //textView.setTextIsSelectable(true);
                //textView.movementMethod = ScrollingMovementMethod()
                //textView.setMovementMethod(LinkMovementMethod.getInstance());
            }
            runOnUiThread {
                dialog.dismiss()

                val obj_adapter : Adapter3
                obj_adapter = Adapter3(applicationContext,arrayList_details)
                listView_details.adapter=obj_adapter

            }
            if(listData.isEmpty())
            {
                Toast.makeText(getApplicationContext(), //Context
                    "似乎沒有抓到資料呢，呵.jpg", // Message to display
                    Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
                ).show(); //Finally Show the toast
            }
            dialog.dismiss()
        } catch (e: JSONException) {
            e.printStackTrace()

            Toast.makeText(getApplicationContext(), //Context
                "ERROR", // Message to display
                Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
            ).show(); //Finally Show the toast
            dialog.dismiss()
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