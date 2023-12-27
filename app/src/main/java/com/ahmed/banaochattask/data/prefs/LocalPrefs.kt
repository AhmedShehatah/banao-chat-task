package com.ahmed.banaochattask.data.prefs


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class LocalPref(private val context: Context) {
    private var sharedSettings: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    @SuppressLint("CommitPrefEdits")
    private fun openConnection() {
        sharedSettings = context.getSharedPreferences(
            APP_PREFERENCES, Context.MODE_PRIVATE
        )
        editor = sharedSettings!!.edit()
    }

    private fun closeConnection() {
        editor = null
        sharedSettings = null
    }


    fun setString(key: String?, value: String?) {
        openConnection()
        editor!!.putString(key, value)
        editor!!.commit()
        closeConnection()
    }


    fun getString(key: String?): String? {
        var result: String? = ""
        openConnection()
        if (sharedSettings?.contains(key) == true) {
            result = sharedSettings?.getString(key, "")
        }
        closeConnection()
        return result
    }


    companion object {
        const val APP_PREFERENCES = "Banao Task"
    }
}