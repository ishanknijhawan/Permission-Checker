package com.ishanknijhawan.listapps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.jar.Manifest

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
        val icon: ImageView = itemView.iv_icon
        val appName = itemView.tv_appname

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
