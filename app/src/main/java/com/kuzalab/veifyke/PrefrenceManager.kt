/*
 * *
 *  * Created by Kogi Eric  on 5/21/19 1:57 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/21/19 1:57 PM
 *
 */

package com.kuzalab.veifyke

import android.content.Context
import android.content.SharedPreferences

class PrefrenceManager(internal var _context: Context) {
    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor
    internal var PRIVATE_MODE = 0

    companion object {
        private val SECRETKEY = "secretkey"
        private val CONSUMERKEY = "consumerkey"

        private val PREF_NAME = "verify_ke_prefrences"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun savekeys(secretKey: String, consumerKey: String) {
        editor.putString(SECRETKEY, secretKey)
        editor.putString(CONSUMERKEY, consumerKey)

        editor.commit()
    }

    fun getSecretKey(): String? {
        return pref.getString(SECRETKEY, null)
    }

    fun getConsumerKey(): String? {
        return pref.getString(CONSUMERKEY, null)
    }


}