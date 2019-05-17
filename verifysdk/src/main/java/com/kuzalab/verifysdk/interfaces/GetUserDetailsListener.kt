/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.verifysdk.interfaces

import com.kuzalab.verifysdk.models.Person
import com.kuzalab.verifysdk.models.VerifyException

interface GetUserDetailsListener {
    fun onCallStarted()
    fun onResponse(person: Person)
    fun onFailure(verifyException: VerifyException)
}