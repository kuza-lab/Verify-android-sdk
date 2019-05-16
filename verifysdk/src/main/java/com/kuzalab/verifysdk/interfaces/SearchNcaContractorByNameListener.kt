package com.kuzalab.verifysdk.interfaces


import com.kuzalab.verifysdk.models.NcaContractor
import com.kuzalab.verifysdk.models.VerifyException

interface SearchNcaContractorByNameListener {
    fun onCallStarted()
    fun onResponse(ncaContractor: List<NcaContractor>)
    fun onFailure(verifyException: VerifyException)
}