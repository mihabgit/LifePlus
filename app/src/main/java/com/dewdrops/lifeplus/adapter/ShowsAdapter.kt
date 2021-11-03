package com.dewdrops.lifeplus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.dewdrops.lifeplus.R
import com.dewdrops.lifeplus.datasource.model.Show

class ShowsAdapter(context: Context, private var showsList: List<Show>) : RecyclerView.Adapter<ShowsAdapter.ShowsViewHolder>() {

    private var mContext: Context? = context
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_shows, parent, false)
        return ShowsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return showsList.size
    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {

        val tvShow = showsList[position]

        val name = tvShow.show.name
        val type = tvShow.show.type
        val language = tvShow.show.language
        holder.tvShowName.text = "Name : $name"
        holder.tvShowType.text = "Type : $type"
        holder.tvShowLanguage.text = "Language : $language"

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClickListener(tvShow)
            println("babu : sfalksjf")
        }
    }

    interface OnItemClickListener {
        fun onItemClickListener(tvShow: Show)
    }

    class ShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvShowName: AppCompatTextView = itemView.findViewById(R.id.tvShowName)
        val tvShowType: AppCompatTextView = itemView.findViewById(R.id.tvShowType)
        val tvShowLanguage: AppCompatTextView = itemView.findViewById(R.id.tvShowLanguage)
    }
}