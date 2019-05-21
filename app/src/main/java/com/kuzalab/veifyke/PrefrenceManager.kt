/*
 * *
 *  * Created by Kogi Eric  on 5/21/19 2:06 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/21/19 2:06 PM
 *
 */

package com.kuzalab.veifyke

import android.content.Context
import android.content.SharedPreferences

class PrefrenceManager(_context: Context) {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0

    companion object {
        private const val SECRETKEY = "secretkey"
        private const val CONSUMERKEY = "consumerkey"
        private const val PREF_NAME = "verify_ke_prefrences"
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