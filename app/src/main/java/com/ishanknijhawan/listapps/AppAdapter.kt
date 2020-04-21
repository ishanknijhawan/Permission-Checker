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
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.jar.Manifest

class AppAdapter(val items: MutableList<AppName>,val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false))
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var bool = ""
        val string: StringBuffer? = null
        try {
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(items[position].packageName, PackageManager.GET_PERMISSIONS)
            string?.append(packageInfo.packageName+"*******:\n")
            val requestedPermissions = packageInfo.requestedPermissions
            if(requestedPermissions != null) {
                for (element in requestedPermissions) {
                    string?.append(element + "\n")
                }
                string?.append("\n")
            }
            val apk = ApkInfoExtractor(context)

            bool = if (PackageManager.PERMISSION_GRANTED == context.packageManager.checkPermission(android.Manifest.permission.RECORD_AUDIO
                    ,items[position].packageName)){
                "true"
            } else
                "false"

            holder.appName.text = items[position].appName
            holder.itemView.setOnClickListener {
                //Toast.makeText(context, bool,Toast.LENGTH_SHORT).show()
                //val intent = pm.getLaunchIntentForPackage(items[position])
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:${items[position].packageName}")
                context.startActivity(intent)
            }

            val icon = context.packageManager.getApplicationIcon(items[position].packageName)
            holder.icon.setImageDrawable(icon)

        }catch (e: Exception){
            Toast.makeText(context, e.printStackTrace().toString() ,Toast.LENGTH_SHORT).show()
        }
    }

}

class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val icon: ImageView = itemView.iv_icon
    val appName = itemView.tv_appname
}
