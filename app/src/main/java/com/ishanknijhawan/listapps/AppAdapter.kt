package com.ishanknijhawan.listapps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(
    val items: MutableList<AppName>,
    val context: Context,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AppAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.appName.text = items[position].appName
        holder.icon.setImageDrawable(items[position].appIcon)

        holder.setOnClick(itemClickListener, position)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.iv_icon)
        val appName: TextView = itemView.findViewById(R.id.tv_appname)

        fun setOnClick(clickListener: OnItemClickListener, position: Int) {
            itemView.setOnClickListener {
                clickListener.onItemClicked(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

}
