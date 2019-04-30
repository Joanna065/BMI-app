package com.example.bmi

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import logic.DataBmi

class SharedPreference(myContext: Context) {

    companion object {
        const val SHARED_PREF = "Bmi shared preference"
    }

    private val sharedPref: SharedPreferences = myContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    fun save(keyName: String, list: MutableList<DataBmi>) {
        val listJson = Gson().toJson(list)
        val editor = sharedPref.edit()
        editor.putString(keyName, listJson)
        editor!!.commit()
    }

    fun getValueList(keyName: String): MutableList<DataBmi> {
        class Token : TypeToken<MutableList<DataBmi>>()
        val keyList = sharedPref.getString(keyName, null)
        if (keyList != null) {
            return Gson().fromJson(keyList, Token().type)
        }
        return mutableListOf()
    }

    fun clearSharedPreference() {
        val editor = sharedPref.edit()
        editor.clear()
        editor.commit()
    }

    fun removeValue(keyName: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(keyName)
        editor.commit()
    }
}