package com.kuzalab.verifysdk

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kuzalab.verifysdk.models.ErrorBody
import com.kuzalab.verifysdk.models.VerifyException
import retrofit2.Response


class ErrorUtils {

    fun parseError(response: Response<*>): VerifyException {

        try {
            val gson = Gson()
            val type = object : TypeToken<ErrorBody>() {}.type
            val erroBody: ErrorBody? = gson.fromJson(response.errorBody()!!.charStream(), type)




            return VerifyException(erroBody?.message, erroBody?.message, erroBody?.errors)

        } catch (e: Exception) {

            return VerifyException("Error Loading Data")

        }

    }
}