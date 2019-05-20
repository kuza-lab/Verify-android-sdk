/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 6:30 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 6:30 PM
 *
 */

package com.kuzalab.verifysdk.utils

import com.kuzalab.verifysdk.models.VerifyException
import retrofit2.Call
import java.io.IOException


internal class FailureUtils {

    fun parseError(call: Call<*>?, t: Throwable?): VerifyException {


        try {
            var ve: VerifyException? = null
            if (call != null) {
                if (call.isCanceled) {
                    ve = VerifyException("Canceled By User")
                } else {
                    if (t != null) {
                        if (t is IOException) {
                            ve = VerifyException("Network failure. Please retry")

                        } else {
                            ve = VerifyException(t.cause?.message)
                        }
                    }
                }
            } else {
                ve = VerifyException(t?.message)
            }


            if (ve == null) {
                ve = VerifyException(" Unknown Error ")
            }
            return ve
        } catch (ex: Exception) {
            return VerifyException("Error Loading Data")

        }


    }
}