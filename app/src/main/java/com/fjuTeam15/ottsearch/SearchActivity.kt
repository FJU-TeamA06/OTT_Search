package com.fjuTeam15.ottsearch

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import android.util.Log.d as logD

class SearchActivity : AppCompatActivity() {


    private lateinit var listViewdetails: ListView

    private lateinit var dialog: AlertDialog
    private var requestQueue: RequestQueue? = null
    private val activityTag = "LogJSON"

    private var arrayListDetails:ArrayList<Model> = ArrayList()
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
                    .setMessage(getString(R.string.app_info)
                    )
                    .setTitle(getString(R.string.app_info_title))
                    .setPositiveButton("OK") { _, _ ->
                        AlertDialog.Builder(this)
                            .setMessage(getString(R.string.app_warning)
                            )
                            .setTitle(getString(R.string.app_warning_title))
                            .setPositiveButton("OK", null)
                            .show()

                    }
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
        title = getString(R.string.ButtonSearchText)
        setTheme(R.style.Theme_OTTSearch);


        val editText: TextInputEditText = findViewById(R.id.et_Name)
        editText.setOnEditorActionListener { view, actionId, _ ->
            //判断 actionId
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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
                .setMessage(getString(R.string.app_info)
                )
                .setTitle(getString(R.string.app_info_title))
                .setPositiveButton("OK") { _, _ ->
                    AlertDialog.Builder(this)
                        .setMessage(getString(R.string.app_warning)
                        )
                        .setTitle(getString(R.string.app_warning_title))
                        .setPositiveButton("OK", null)
                        .show()

                }
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
            Toast.makeText(
                applicationContext, //Context
                "搜尋不得為空", // Message to display
                Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
            ).show() //Finally Show the toast
        }
        else
        {
            val debugTag = "NetworkStatusExample"
            var isWifiConn = false
            var isMobileConn = false
            val connectivityManager = getSystemService(ConnectivityManager::class.java)
            val currentNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork)

            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    isWifiConn = true
                }
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    isMobileConn = true
                }
            }

            Log.d(debugTag, "Wifi connected: $isWifiConn")
            Log.d(debugTag, "Mobile connected: $isMobileConn")
            if(isWifiConn or isMobileConn)
            {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setCancelable(false) // if you want user to wait for some process to finish,
                builder.setView(R.layout.layout_loading_dialog)
                dialog = builder.create()
                requestQueue = Volley.newRequestQueue(this)

                //jsonParse()
                //listOf(logD(ACTIVITY_TAG, jsonParse().toString()))
                listViewdetails = findViewById(R.id.userlist)
                listViewdetails.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _ ->
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(arrayListDetails[position].url))
                    startActivity(browserIntent)
                }
                initializedata()
                jsonParse()
            }
            else
            {
                Toast.makeText(
                    applicationContext, //Context
                    "沒有網際網路連線", // Message to display
                    Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
                ).show() //Finally Show the toast
            }

        }

    }
    private fun initializedata() {
        arrayListDetails = arrayListOf()
        listViewdetails.adapter = Adapter3(this,arrayListDetails)
    }
    
    private fun jsonParse() {
        dialog.show()
        val editText:EditText = findViewById(R.id.et_Name)
        val listView:ListView = findViewById(R.id.userlist)
        val keyword=editText.text
        val switchBtn = findViewById<SwitchMaterial>(R.id.switch1)
        var url: String = if(switchBtn.isChecked) {
            "http://140.136.151.129:8088/getkeyword?keyword=$keyword&scrape=1"
        } else {
            "http://140.136.151.129:8088/getkeyword?keyword=$keyword&scrape=0"
        }

        val request = JsonObjectRequest(Request.Method.GET, url, null, {
                response ->try {
            val jsonArray = response.getJSONArray("data")
            val listData = ArrayList<String>()

            for (i in 0 until jsonArray.length()) {
                val dataJson = jsonArray.getJSONObject(i)
                val model = Model()
                model.title=dataJson.getString("Title")
                model.platform=dataJson.getString("Platform")
                model.url=dataJson.getString("URL")
                arrayListDetails.add(model)
                //textView.append("Title: $firstName\nPlatform: $age\nURL: $mail\n---------\n")
                listData.add(model.toString())

                logD(activityTag, model.title+"|"+model.platform+"|"+model.url)
                //textView.setTextIsSelectable(true);
                //textView.movementMethod = ScrollingMovementMethod()
                //textView.setMovementMethod(LinkMovementMethod.getInstance());
            }
            runOnUiThread {
                dialog.dismiss()

                val objAdapter : Adapter3
                listViewdetails.adapter=Adapter3(applicationContext,arrayListDetails)

            }
            if(listData.isEmpty())
            {
                Toast.makeText(
                    applicationContext, //Context
                    "似乎沒有抓到資料呢，呵.jpg", // Message to display
                    Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
                ).show() //Finally Show the toast
            }
            dialog.dismiss()
        } catch (e: JSONException) {
            e.printStackTrace()
            dialog.dismiss()
            Toast.makeText(
                applicationContext, //Context
                "ERROR", // Message to display
                Toast.LENGTH_SHORT // Duration of the message, another possible value is Toast.LENGTH_LONG
            ).show() //Finally Show the toast
            dialog.dismiss()
        }
        }, {  })
        request.retryPolicy = DefaultRetryPolicy(
            100000,
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue?.add(request)
    }
}