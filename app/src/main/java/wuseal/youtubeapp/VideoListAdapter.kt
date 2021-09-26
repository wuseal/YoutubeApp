package wuseal.youtubeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import coil.load

class VideoListAdapter(private val items:List<VideoItem>) :BaseAdapter() {
    override fun getCount(): Int {

        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
       val itemView =  if (p1 != null) {
            p1
        }else{
           val item = LayoutInflater.from(p2!!.context).inflate(R.layout.item_video,p2,false)
           ViewHolder().apply {
               imaegView = item.findViewById(R.id.iv_video)
               title = item.findViewById(R.id.tv_title)
               playIcon = item.findViewById(R.id.iv_play)
               item.tag = this
           }
           item
        }
        val holder = itemView.tag as ViewHolder

        val item = getItem(p0) as VideoItem
        holder.imaegView.load(item.thumbnail)
        holder.title.text = item.title
        return itemView

    }

    class ViewHolder{
        lateinit var imaegView:ImageView
        lateinit var playIcon:ImageView
        lateinit var title:TextView

    }
}