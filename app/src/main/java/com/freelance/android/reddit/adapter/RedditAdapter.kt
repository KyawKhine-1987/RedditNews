package com.freelance.android.reddit.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.freelance.android.reddit.R
import com.freelance.android.reddit.model.RedditModel
import java.util.*

class RedditAdapter(private val listener : Listener,
                    private val mParentData: RedditModel) : RecyclerView.Adapter<RedditAdapter.MyViewHolder>() {

    private val LOG_TAG = RedditAdapter::class.java.getName()

    interface Listener {

        fun onItemClick(redditModel : RedditModel.ParentData.Children)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() called...")

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() called...")

        val data: RedditModel.ParentData.Children? = mParentData.data.children[position]
        holder.author.text = data?.data?.author
        holder.image.loadImg(data?.data!!.thumbnail)
        holder.time.text = data.data.created.getFriendlyTime()
        holder.comments.text = data.data.num_comments.toString() + " comments"
        holder.title.text = data.data.title

        //ivThumbnail.loadImg(data?.data?.thumbnail)
    }

    override fun getItemCount(): Int {
        Log.i(LOG_TAG, "TEST: getItemCount() called...")

        return mParentData.data.children.count() //check the code
    }

    fun Long.getFriendlyTime(): String {
        Log.i(LOG_TAG, "TEST: getFriendlyTime() called...")

        val dateTime = Date(this * 1000)
        val sb = StringBuffer()
        val current = Calendar.getInstance().time
        var diffInSeconds = ((current.time - dateTime.time) / 1000).toInt()

        val sec = if (diffInSeconds >= 60) (diffInSeconds % 60).toInt() else diffInSeconds.toInt()
        diffInSeconds = diffInSeconds / 60
        val min = if (diffInSeconds >= 60) (diffInSeconds % 60).toInt() else diffInSeconds.toInt()
        diffInSeconds = diffInSeconds / 60
        val hrs = if (diffInSeconds >= 24) (diffInSeconds % 24).toInt() else diffInSeconds.toInt()
        diffInSeconds = diffInSeconds / 24
        val days = if (diffInSeconds >= 30) (diffInSeconds % 30).toInt() else diffInSeconds.toInt()
        diffInSeconds = diffInSeconds / 30
        var months = if (diffInSeconds >= 12) (diffInSeconds % 12).toInt() else diffInSeconds.toInt()
        diffInSeconds = diffInSeconds / 12
        val years = diffInSeconds.toInt()

        if (years > 0) {
            if (years == 1) {
                sb.append("a year")
            } else {
                sb.append("$years years")
            }
            if (years <= 6 && months > 0) {
                if (months == 1) {
                    sb.append(" and a month")
                } else {
                    sb.append(" and $months months")
                }
            }
        } else if (months > 0) {
            if (months == 1) {
                sb.append("a month")
            } else {
                sb.append("$months months")
            }
            if (months <= 6 && days > 0) {
                if (days == 1) {
                    sb.append(" and a day")
                } else {
                    sb.append(" and $days days")
                }
            }
        } else if (days > 0) {
            if (days == 1) {
                sb.append("a day")
            } else {
                sb.append("$days days")
            }
            if (days <= 3 && hrs > 0) {
                if (hrs == 1) {
                    sb.append(" and an hour")
                } else {
                    sb.append(" and $hrs hours")
                }
            }
        } else if (hrs > 0) {
            if (hrs == 1) {
                sb.append("an hour")
            } else {
                sb.append("$hrs hours")
            }
            if (min > 1) {
                sb.append(" and $min minutes")
            }
        } else if (min > 0) {
            if (min == 1) {
                sb.append("a minute")
            } else {
                sb.append("$min minutes")
            }
            if (sec > 1) {
                sb.append(" and $sec seconds")
            }
        } else {
            if (sec <= 1) {
                sb.append("about a second")
            } else {
                sb.append("about $sec seconds")
            }
        }

        sb.append(" ago")

        return sb.toString()
    }

    fun ImageView.loadImg(imageUrl: String) {
        Log.i(LOG_TAG, "TEST: loadImg() called...")

        if (TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(R.mipmap.ic_launcher).into(this)
        } else {
            Glide.with(context).load(imageUrl).into(this)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val LOG_TAG = MyViewHolder::class.java.getName()

        val title = itemView.findViewById(R.id.tvTitle) as TextView
        val author = itemView.findViewById(R.id.tvAuthor) as TextView
        val comments = itemView.findViewById(R.id.tvComments) as TextView
        val time = itemView.findViewById(R.id.tvTime) as TextView
        val image = itemView.findViewById(R.id.ivThumbnail) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Log.i(LOG_TAG, "TEST: onClick() in MyViewHolder is called...")

            listener.onItemClick(mParentData.data.children[adapterPosition])
        }

    }
}