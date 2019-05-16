package com.kuzalab.verifysdk.interfaces

import com.kuzalab.verifysdk.models.Person
import com.kuzalab.verifysdk.models.VerifyException

interface GetUserDetailsListener {
    fun onCallStarted()
    fun onResponse(person: Person)
    fun onFailure(verifyException: VerifyException)
}