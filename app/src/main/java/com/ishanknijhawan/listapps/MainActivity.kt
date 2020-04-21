package com.ishanknijhawan.listapps

import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ishanknijhawan.listapps.R.style.Theme_AppCompat_DayNight_NoActionBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (NotificationManagerCompat.from(this).areNotificationsEnabled())
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()


        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pkgAppsList: List<ResolveInfo> = packageManager.queryIntentActivities(mainIntent, 0)

        val pm = packageManager
        val packages =
            pm.getInstalledPackages(PackageManager.GET_META_DATA)

        val p = packageManager
        val appinstall =
        p.getInstalledPackages(0)

        val finalList = mutableListOf<AppName>()
        val apk = ApkInfoExtractor(this)
        
        for (i in 0 until packages.size){
            val packageName = packages[i].packageName
            if (pm.getLaunchIntentForPackage(packageName) != null){
                finalList.add(AppName(packageName,apk.GetAppName(packageName).toString()))
            }
        }

        finalList.sortBy { it.appName }

        rv_main_list.layoutManager = LinearLayoutManager(this)
        rv_main_list.adapter = AppAdapter(finalList, this)
        tvCount.text = "${finalList.size} apps found"

        switch2.setOnClickListener {
            if (switch2.isChecked){
                Toast.makeText(this, "on", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "off", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
