package com.fjuTeam15.ottsearch

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import android.util.Log.d as logD

class SearchActivity : AppCompatActivity() {


    lateinit var listView_details: ListView

    private lateinit var dialog: AlertDialog
    private var requestQueue: RequestQueue? = null
    private val ACTIVITY_TAG = "LogJSON"

    var arrayList_details:ArrayList<Model> = ArrayList()
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu.
        val inflater = menuInflater
        inflater.inflate(R.menu.searchviewmenu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_info -> {
                // Show a dialog with help information.
                AlertDialog.Builder(this)
                    .setMessage("請盡可能以完整片名搜尋\n否則可能會無法載入"
                    )
                    .setTitle("使用說明")
                    .setPositiveButton("OK", { _, _ ->
                        AlertDialog.Builder(this)
                            .setMessage("\"抓取線上資料\"開啟時可能會搜尋超時\n" +
                                    "如果1分鐘以上無回應\n" +
                                    "請殺掉APP重啟")
                            .setTitle("警告")
                            .setPositiveButton("OK", null)
                            .show()

                    })
                    .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarSearch)
        setSupportActionBar(toolbar)
        title = "查詢"


        val editText:EditText = findViewById(R.id.et_Name)
        editText.setOnEditorActionListener { view, actionId, event ->
            //判断 actionId
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                //do something...
                switchToSend(view)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        //第一次打開顯示提示，之後都不會顯示
        if (!sharedPreferences.getBoolean("displayed_hint", false)) {
            // Show the hint.
            AlertDialog.Builder(this)
                .setMessage("請盡可能以完整片名搜尋\n否則可能會無法載入"
                )
                .setTitle("使用說明")
                .setPositiveButton("OK", { _, _ ->
                    AlertDialog.Builder(this)
                        .setMessage("\"抓取線上資料\"開啟時可能會搜尋超時\n" +
                                "如果1分鐘以上無回應\n" +
                                "請殺掉APP重啟")
                        .setTitle("警告")
                        .setPositiveButton("OK", null)
                        .show()

                })
                .show()

            // Save the fact that the hint has been displayed.
            val editor = sharedPreferences.edit()
            editor.putBoolean("displayed_hint", true)
            editor.apply()
        }


    }

    fun switchToSend(view: View) {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        val editText:EditText = findViewById(R.id.et_Name)
        val keyword=editText.text
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(getApplicationContext(), //Context
                "搜尋不得為空", // Message to display
                Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
            ).show() //Finally Show the toast
        }
        else
        {
            val DEBUG_TAG = "NetworkStatusExample"
            val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var isWifiConn: Boolean = false
            var isMobileConn: Boolean = false
            connMgr.allNetworks.forEach { network ->
                connMgr.getNetworkInfo(network).apply {
                    if (this?.type?.equals(ConnectivityManager.TYPE_WIFI) == true) {
                        isWifiConn = isWifiConn or isConnected
                    }
                    if (this?.type?.equals(ConnectivityManager.TYPE_MOBILE) == true) {
                        isMobileConn = isMobileConn or isConnected
                    }
                }
            }
            Log.d(DEBUG_TAG, "Wifi connected: $isWifiConn")
            Log.d(DEBUG_TAG, "Mobile connected: $isMobileConn")
            if(isWifiConn or isMobileConn)
            {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setCancelable(false) // if you want user to wait for some process to finish,
                builder.setView(R.layout.layout_loading_dialog)
                dialog = builder.create()
                requestQueue = Volley.newRequestQueue(this)

                //jsonParse()
                //listOf(logD(ACTIVITY_TAG, jsonParse().toString()))
                listView_details = findViewById<ListView>(R.id.userlist) as ListView
                listView_details.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id ->
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(arrayList_details.get(position).url))
                    startActivity(browserIntent)
                }
                initializedata()
                jsonParse()
            }
            else
            {
                Toast.makeText(getApplicationContext(), //Context
                    "沒有網際網路連線", // Message to display
                    Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
                ).show() //Finally Show the toast
            }

        }

    }
    private fun initializedata() {
        arrayList_details = arrayListOf()
        listView_details.adapter = Adapter3(this,arrayList_details)
    }
    
    private fun jsonParse() {
        dialog.show()
        val editText:EditText = findViewById(R.id.et_Name)
        val listView:ListView = findViewById(R.id.userlist)
        val keyword=editText.text
        var switchBtn = findViewById<Switch>(R.id.switch1)
        var url = ""
        if(switchBtn.isChecked)
        {
            url="http://140.136.151.129:8088/getkeyword?keyword=$keyword&scrape=1"
        }
        else
        {
            url="http://140.136.151.129:8088/getkeyword?keyword=$keyword&scrape=0"
        }

        val request = JsonObjectRequest(Request.Method.GET, url, null, {
                response ->try {
            val jsonArray = response.getJSONArray("data")
            val listData = ArrayList<String>()

            data class VD(val title: String, val platform: String, val URL: String)

            for (i in 0 until jsonArray.length()) {
                val DataJson = jsonArray.getJSONObject(i)
                val model = Model()
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
                ).show() //Finally Show the toast
            }
            dialog.dismiss()
        } catch (e: JSONException) {
            e.printStackTrace()
            dialog.dismiss()
            Toast.makeText(getApplicationContext(), //Context
                "ERROR", // Message to display
                Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
            ).show() //Finally Show the toast
            dialog.dismiss()
        }
        }, {  })
        request.setRetryPolicy(
            DefaultRetryPolicy(
                100000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue?.add(request)
    }
}