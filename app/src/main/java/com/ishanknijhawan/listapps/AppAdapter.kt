package com.ishanknijhawan.listapps

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.jar.Manifest

class AppAdapter(val items: MutableList<String>,val context: Context) :
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
            val packageInfo = pm.getPackageInfo(items[position], PackageManager.GET_PERMISSIONS)
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
                    ,items[position])){
                "true"
            } else
                "false"

            holder.appName.text = apk.GetAppName(items[position])
            holder.itemView.setOnClickListener {
                //Toast.makeText(context, bool,Toast.LENGTH_SHORT).show()
                val intent = pm.getLaunchIntentForPackage(items[position])
                if (intent != null)
                    context.startActivity(intent)
                else
                    Toast.makeText(context, "App not user accessible", Toast.LENGTH_SHORT).show()
            }

            val icon = context.packageManager.getApplicationIcon(items[position])
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
