package com.fjuTeam15.ottsearch

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView

class Adapter3(context: Context,arrayListDetails:ArrayList<Model>) : BaseAdapter(){

    private val layoutInflater: LayoutInflater
    private val arrayListDetails:ArrayList<Model>
    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails=arrayListDetails
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?  {
        val view: View?
        val listRowHolder: ListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.list_json, parent, false)
            listRowHolder = ListRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ListRowHolder
        }

        listRowHolder.tvid.text = arrayListDetails.get(position).title
        listRowHolder.tvplatform.text = arrayListDetails.get(position).platform
        listRowHolder.tvurl.text = arrayListDetails.get(position).url
        
        return view
    }

}

private class ListRowHolder(row: View?) {
    public val tvid: TextView
    public val tvplatform: TextView
    public val tvurl: TextView
    public val linearLayout: LinearLayout

    init {
        this.tvid = row?.findViewById<TextView>(R.id.tv_Id) as TextView
        this.tvplatform = row?.findViewById<TextView>(R.id.tv_platform) as TextView
        this.tvurl = row?.findViewById<TextView>(R.id.tv_url) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
    }
}