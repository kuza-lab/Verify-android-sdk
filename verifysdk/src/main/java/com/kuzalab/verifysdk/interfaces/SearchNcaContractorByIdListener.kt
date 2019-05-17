/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.verifysdk.interfaces


import com.kuzalab.verifysdk.models.NcaContractor
import com.kuzalab.verifysdk.models.VerifyException

interface SearchNcaContractorByIdListener {
    fun onCallStarted()
    fun onResponse(ncaContractor: NcaContractor)
    fun onFailure(verifyException: VerifyException)
}