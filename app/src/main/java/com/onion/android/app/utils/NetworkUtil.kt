package com.onion.android.app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

object NetworkUtil {


    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun isWifiConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            ?: return true
        val network = manager.activeNetwork
        val capabilities = manager.getNetworkCapabilities(network)!!
        return !capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI or NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}