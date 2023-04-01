package com.example.sharedpreferences

import android.content.Context

internal class UserPreference(context : Context ) {

    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUser(value : UsersModel){
        val editor = preference.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putInt(AGE, value.age)
        editor.putString(PHONE_NUMBER, value.no)
        editor.putBoolean(ISLOVEMU, value.isLove)
        editor.apply()
    }

    fun getUser(): UsersModel{
        val model = UsersModel()
        model.name = preference.getString(NAME , "")
        model.email= preference.getString(EMAIL, "")
        model.age = preference.getInt(AGE, 0)
        model.no = preference.getString(PHONE_NUMBER, "")
        model.isLove = preference.getBoolean(ISLOVEMU, false)

        return model
    }


    companion object{
        private const val PREF_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val ISLOVEMU = "isLove"
    }
}