package com.freelance.android.reddit.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.freelance.android.reddit.R
import com.freelance.android.reddit.model.RedditModel

class RedditAdapter(private val context: Context,
                    private val mParentData: RedditModel) : RecyclerView.Adapter<RedditAdapter.MyViewHolder>() {

    private val LOG_TAG = RedditAdapter::class.java.getName()

    init {
        Log.i(LOG_TAG, "TEST: RedditAdapter() constructor called...")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() called...")

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_news_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() called...")


        val data: RedditModel.ParentData.Children? = mParentData.data.children[position]
        holder.author.text = data?.data?.author
        holder.comments.text = data?.data?.num_comments.toString() + " comments"
        holder.title.text = data?.data?.title


        /*holder.author.text = data?.data!!.children!!.get(0).data!!.author
        holder.comments.text = data?.data!!.children!!.get(0).data!!.num_comments.toString()
        holder.title.text = data?.data!!.children!!.get(0).data!!.title*/

        /*Glide.with(this).load().
        holder.image.
        holder.time.text = redditModel?.created.getFriendTime()*/


    }

    override fun getItemCount(): Int {
        Log.i(LOG_TAG, "TEST: getItemCount() called...")

        return mParentData.data.children.count() //check the code
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val LOG_TAG = MyViewHolder::class.java.getName()

        val title = itemView.findViewById(R.id.tvTitle) as TextView
        val author = itemView.findViewById(R.id.tvAuthor) as TextView
        val comments = itemView.findViewById(R.id.tvComments) as TextView
        val time = itemView.findViewById(R.id.tvTime) as TextView
        val image = itemView.findViewById(R.id.ivThumbnail) as ImageView
    }

}