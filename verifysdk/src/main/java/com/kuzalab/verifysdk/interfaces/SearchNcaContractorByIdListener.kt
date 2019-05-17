package com.kuzalab.verifysdk.interfaces


import com.kuzalab.verifysdk.models.NcaContractor
import com.kuzalab.verifysdk.models.VerifyException

interface SearchNcaContractorByIdListener {
    fun onCallStarted()
    fun onResponse(ncaContractor: NcaContractor)
    fun onFailure(verifyException: VerifyException)
}