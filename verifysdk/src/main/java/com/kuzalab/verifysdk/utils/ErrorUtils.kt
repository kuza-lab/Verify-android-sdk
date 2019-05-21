/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 3:58 PM
 *
 */

package com.kuzalab.verifysdk.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kuzalab.verifysdk.models.ErrorBody
import com.kuzalab.verifysdk.models.VerifyException
import retrofit2.Response


internal class ErrorUtils {

    fun parseError(response: Response<*>): VerifyException {

        try {
            val gson = Gson()
            val type = object : TypeToken<ErrorBody>() {}.type
            val erroBody: ErrorBody? = gson.fromJson(response.errorBody()!!.charStream(), type)




            return VerifyException(erroBody?.message, erroBody?.status_name, erroBody?.errors)

        } catch (e: Exception) {

            return VerifyException("Error Loading Data")

        }

    }
}