package com.ishanknijhawan.listapps

import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


class ApkInfoExtractor(context2: Context) {
    var context1: Context
    fun GetAllInstalledApkInfo(): List<String> {
        val ApkPackageName: MutableList<String> = ArrayList()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        val resolveInfoList: List<ResolveInfo> =
            context1.getPackageManager().queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfoList) {
            val activityInfo = resolveInfo.activityInfo
            if (!isSystemPackage(resolveInfo)) {
                ApkPackageName.add(activityInfo.applicationInfo.packageName)
            }
        }
        return ApkPackageName
    }

    fun isSystemPackage(resolveInfo: ResolveInfo): Boolean {
        return resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    fun getAppIconByPackageName(ApkTempPackageName: String?): Drawable? {
        val drawable: Drawable
        drawable =
            context1.packageManager.getApplicationIcon(ApkTempPackageName)
        return drawable
    }

    fun GetAppName(ApkPackageName: String?): String? {
        var Name = ""
        val applicationInfo: ApplicationInfo
        val packageManager: PackageManager = context1.packageManager
        try {
            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0)
            if (applicationInfo != null) {
                Name = packageManager.getApplicationLabel(applicationInfo) as String
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return Name
    }

    init {
        context1 = context2
    }
}