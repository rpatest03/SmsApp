package com.e.app.utils

import android.content.Context
import android.content.SharedPreferences

import com.e.app.utils.Session.Key.APP_AUTH

class Session(context: Context) {

    private val prefName = "private_sms"
    private val privateMode = 0
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        pref = context.getSharedPreferences(prefName, privateMode)
        editor = pref.edit()
    }

    fun getString(): String {
        return pref.getString(APP_AUTH, "")!!
    }

    fun setAppUserToken(token: String) {
        editor.putString(APP_AUTH, token)
        editor.apply()
    }


    object Key {
        internal const val APP_AUTH = "app_auth"
    }
}