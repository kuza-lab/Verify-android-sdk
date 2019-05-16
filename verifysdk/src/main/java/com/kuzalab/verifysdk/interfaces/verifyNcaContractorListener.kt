package com.kuzalab.verifysdk.interfaces


import com.kuzalab.verifysdk.models.ParamsResponse
import com.kuzalab.verifysdk.models.VerifyException

interface verifyNcaContractorListener {
    fun onCallStarted()
    fun onResponse(paramsResponse: List<ParamsResponse>)
    fun onFailure(verifyException: VerifyException)
}