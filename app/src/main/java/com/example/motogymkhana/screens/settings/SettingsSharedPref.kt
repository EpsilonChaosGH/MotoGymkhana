package com.example.motogymkhana.screens.settings

import android.content.Context
import com.example.motogymkhana.Const

class SettingsSharedPref(
    appContext: Context
) {

    private val sharedPreferences =
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun setControllerIp(ip: String) {
        sharedPreferences.edit()
            .putString(PREF_CONTROLLER_IP, ip)
            .apply()
    }

    fun getControllerIp(): String =
        sharedPreferences.getString(PREF_CONTROLLER_IP, Const.controllerIp) ?: Const.controllerIp

    fun setRequest(request: String) {
        sharedPreferences.edit()
            .putString(PREF_REQUEST, request)
            .apply()
    }

    fun getRequest(): String =
        sharedPreferences.getString(PREF_REQUEST, Const.controllerRequest) ?: Const.controllerRequest

    companion object {
        private const val PREF_CONTROLLER_IP = "PREF_CONTROLLER_IP"
        private const val PREF_REQUEST = "PREF_REQUEST"
    }
}