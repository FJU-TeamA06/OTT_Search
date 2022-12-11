package com.fjuTeam15.ottsearch

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
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
        val imageView: ImageView
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
        when (arrayListDetails.get(position).platform) {
            "巴哈姆特動畫瘋" -> listRowHolder.imageView.setImageResource(R.drawable.anime_gamer)
            "Netflix" -> listRowHolder.imageView.setImageResource(R.drawable.netflix)
            "Gimy 劇迷" -> listRowHolder.imageView.setImageResource(R.drawable.gimy)
            "楓林網" -> listRowHolder.imageView.setImageResource(R.drawable.imaple)
            "Line TV" -> listRowHolder.imageView.setImageResource(R.drawable.linetv)
            "KKTV" -> listRowHolder.imageView.setImageResource(R.drawable.kktv)
            "Amazon Prime Video" -> listRowHolder.imageView.setImageResource(R.drawable.amazonprimevideo)
            "Disney+" -> listRowHolder.imageView.setImageResource(R.drawable.disneyplus)
            "iTunes" -> listRowHolder.imageView.setImageResource(R.drawable.itunes)
            "Zee5" -> listRowHolder.imageView.setImageResource(R.drawable.zee5)
            "木棉花YouTube官方頻道" -> listRowHolder.imageView.setImageResource(R.drawable.museyt)
            "羚邦YouTube官方頻道" -> listRowHolder.imageView.setImageResource(R.drawable.anioneyt)
            "MUBI" -> listRowHolder.imageView.setImageResource(R.drawable.mubi)
            "Classix"-> listRowHolder.imageView.setImageResource(R.drawable.classix)
            "BiliBili"-> listRowHolder.imageView.setImageResource(R.drawable.bilibili)
            else -> listRowHolder.imageView.setImageResource(R.drawable.ic_baseline_question_mark_24)
        }

        return view
    }

}

private class ListRowHolder(row: View?) {
    public val tvid: TextView
    public val tvplatform: TextView
    public val tvurl: TextView
    public val linearLayout: LinearLayout
    public var imageView: ImageView
    init {
        this.tvid = row?.findViewById<TextView>(R.id.tv_Id) as TextView
        this.tvplatform = row?.findViewById<TextView>(R.id.tv_platform) as TextView
        this.tvurl = row?.findViewById<TextView>(R.id.tv_url) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
        this.imageView = row?.findViewById<ImageView>(R.id.imageView) as ImageView
    }
}