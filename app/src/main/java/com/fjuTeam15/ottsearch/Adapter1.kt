package com.fjuTeam15.ottsearch

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter1(
    private val context: Context,
    private val dataset: List<Item>
) : RecyclerView.Adapter<Adapter1.ItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text10)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
        holder.itemView.setOnClickListener()
        {
            val TAG = "Adapter1"
            val pos = holder.layoutPosition//點擊元件的順位
            Log.d(TAG, "Testing")
            Log.d(TAG, pos.toString())
            if(pos==0)//導向網頁程式碼
            {
                var url="https://www.netflix.com/tw/"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }
            else if(pos==1)
            {
                var url="https://www.disneyplus.com/zh-tw"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }
            else if(pos==2)
            {
                var url="https://ani.gamer.com.tw/"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }
            else if(pos==3)
            {
                var url="https://video.friday.tw/"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }
            else if(pos==4)
            {
                var url="https://play.google.com/store/movies?hl=zh_TW&gl=US"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }

            else if(pos==5)
            {
                var url="https://www.linetv.tw/"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }
            else if(pos==6)
            {
                var url="https://kktv.me/titleList/ranking"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }
            else if(pos==7)
            {
                var url="https://www.bilibili.com/v/popular/rank/bangumi"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }
            else if(pos==8)
            {
                var url="https://www.primevideo.com/"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }
            else if(pos==9)
            {
                var url="https://www.iq.com/?lang=zh_tw"
                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                holder.itemView.context.startActivity(webIntent)
            }


        }
    }
    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}
