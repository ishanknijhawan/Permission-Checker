package com.ishanknijhawan.listapps

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AppAdapter.OnItemClickListener {

    lateinit var permissionsArray: ArrayList<String>
    lateinit var appPackageName: String
    lateinit var appName: String
    lateinit var appInfo: ApplicationInfo
    lateinit var appIcon: Drawable
    lateinit var adapter: AppAdapter
    lateinit var packages: MutableList<PackageInfo>
    private val appList = mutableListOf<AppName>()
    private val cameraList = mutableListOf<AppName>()
    private val contactsList = mutableListOf<AppName>()
    private val locationList = mutableListOf<AppName>()
    private val microphoneList = mutableListOf<AppName>()
    private val storagemediaList = mutableListOf<AppName>()
    private val smsList = mutableListOf<AppName>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionsArray = arrayListOf(
            "All apps",
            "Camera",
            "Contacts",
            "Location",
            "Microphone",
            "Storage & Media",
            "SMS"
        )

        initSpinner()

        packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)

        for (i in 0 until packages.size) {
            appPackageName = packages[i].packageName
            appInfo = packageManager.getApplicationInfo(appPackageName, 0)
            appName = packageManager.getApplicationLabel(appInfo) as String
            appIcon = packageManager.getApplicationIcon(appPackageName)

            if (packageManager.getLaunchIntentForPackage(appPackageName) != null) {
                appList.add(AppName(appPackageName, appName, appIcon))

                if (checkCameraPermission(appPackageName)) {
                    cameraList.add(AppName(appPackageName, appName, appIcon))
                }
                if (checkContactsPermission(appPackageName)) {
                    contactsList.add(AppName(appPackageName, appName, appIcon))
                }
                if (checkLocationPermission(appPackageName)) {
                    locationList.add(AppName(appPackageName, appName, appIcon))
                }
                if (checkMicrophonePermission(appPackageName)) {
                    microphoneList.add(AppName(appPackageName, appName, appIcon))
                }
                if (checkStoragePermission(appPackageName)) {
                    storagemediaList.add(AppName(appPackageName, appName, appIcon))
                }
                if (checkSmsPermission(appPackageName)) {
                    smsList.add(AppName(appPackageName, appName, appIcon))
                }

            }
        }

        appList.sortBy { it.appName }
        cameraList.sortBy { it.appName }
        contactsList.sortBy { it.appName }
        locationList.sortBy { it.appName }
        microphoneList.sortBy { it.appName }
        storagemediaList.sortBy { it.appName }
        smsList.sortBy { it.appName }
        rv_main_list.layoutManager = LinearLayoutManager(this)

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val text = parentView?.getItemAtPosition(position).toString()
                when (text) {
                    permissionsArray[0] -> {    //All apps
                        adapter = AppAdapter(appList, applicationContext, this@MainActivity)
                        rv_main_list.adapter = adapter
                        tvCount.text = "${appList.size} apps found"
                    }
                    permissionsArray[1] -> {    //Camera
                        adapter = AppAdapter(cameraList, applicationContext, this@MainActivity)
                        rv_main_list.adapter = adapter
                        tvCount.text = "${cameraList.size} apps found"
                    }
                    permissionsArray[2] -> {    //Contacts
                        adapter = AppAdapter(contactsList, applicationContext, this@MainActivity)
                        rv_main_list.adapter = adapter
                        tvCount.text = "${contactsList.size} apps found"
                    }
                    permissionsArray[3] -> {    //Location
                        adapter = AppAdapter(locationList, applicationContext, this@MainActivity)
                        rv_main_list.adapter = adapter
                        tvCount.text = "${locationList.size} apps found"
                    }
                    permissionsArray[4] -> {       //Microphone
                        adapter = AppAdapter(microphoneList, applicationContext, this@MainActivity)
                        rv_main_list.adapter = adapter
                        tvCount.text = "${microphoneList.size} apps found"
                    }
                    permissionsArray[5] -> {       //Storage & Media
                        adapter =
                            AppAdapter(storagemediaList, applicationContext, this@MainActivity)
                        rv_main_list.adapter = adapter
                        tvCount.text = "${storagemediaList.size} apps found"
                    }
                    permissionsArray[6] -> {    //Sms
                        adapter = AppAdapter(smsList, applicationContext, this@MainActivity)
                        rv_main_list.adapter = adapter
                        tvCount.text = "${smsList.size} apps found"
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

    }

    private fun checkCameraPermission(appPackageName: String?): Boolean {
        return PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.CAMERA,
            appPackageName
        )
    }

    private fun checkContactsPermission(appPackageName: String?): Boolean {
        return (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.READ_CONTACTS,
            appPackageName
        )) or (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.WRITE_CONTACTS,
            appPackageName
        ))
    }

    private fun checkLocationPermission(appPackageName: String?): Boolean {
        return (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            appPackageName
        )) or (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            appPackageName
        ))
    }

    private fun checkMicrophonePermission(appPackageName: String?): Boolean {
        return PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.RECORD_AUDIO,
            appPackageName
        )
    }

    private fun checkStoragePermission(appPackageName: String?): Boolean {
        return (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            appPackageName
        )) or (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            appPackageName
        ))
    }

    private fun checkSmsPermission(appPackageName: String?): Boolean {
        return (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.READ_SMS,
            appPackageName
        )) or (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.SEND_SMS,
            appPackageName
        )) or (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
            android.Manifest.permission.RECEIVE_SMS,
            appPackageName
        ))
    }

    private fun initSpinner() {
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            permissionsArray
        )

        spinner.adapter = arrayAdapter
    }

    override fun onItemClicked(position: Int) {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

        when (spinner.selectedItemPosition) {
            0 -> {
                intent.data = Uri.parse("package:${appList[position].packageName}")
            }
            1 -> {
                intent.data = Uri.parse("package:${cameraList[position].packageName}")
            }
            2 -> {
                intent.data = Uri.parse("package:${contactsList[position].packageName}")
            }
            3 -> {
                intent.data = Uri.parse("package:${locationList[position].packageName}")
            }
            4 -> {
                intent.data = Uri.parse("package:${microphoneList[position].packageName}")
            }
            5 -> {
                intent.data = Uri.parse("package:${storagemediaList[position].packageName}")
            }
            6 -> {
                intent.data = Uri.parse("package:${smsList[position].packageName}")
            }
        }
        startActivity(intent)
    }
}