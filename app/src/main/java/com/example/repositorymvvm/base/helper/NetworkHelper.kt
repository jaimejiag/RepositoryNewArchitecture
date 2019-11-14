package com.example.repositorymvvm.base.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.lang.ref.WeakReference

class NetworkHelper(context: Context) {
    private val mContext: WeakReference<Context> = WeakReference(context)

    fun isNetworkAvailable(): Boolean {
        val manager = mContext.get()?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
        var isConnected = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkInfo = manager?.activeNetwork
            val capabilities = manager?.getNetworkCapabilities(networkInfo)

            capabilities?.let {
                isConnected = it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        } else {
            val networkInfo = manager?.activeNetworkInfo
            isConnected = networkInfo?.isConnected == true
        }

        return isConnected
    }
}