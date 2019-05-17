/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 4:11 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 2:26 PM
 *
 */

package com.kuzalab.verifysdk.interfaces


import com.kuzalab.verifysdk.models.ParamsResponse
import com.kuzalab.verifysdk.models.VerifyException

interface VerifyNcaContractorListener {
    fun onCallStarted()
    fun onResponse(paramsResponse: List<ParamsResponse>)
    fun onFailure(verifyException: VerifyException)
}