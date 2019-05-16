package com.kuzalab.verifysdk.interfaces

import com.kuzalab.verifysdk.models.Token
import com.kuzalab.verifysdk.models.VerifyException

interface TokenCallListener {
    fun onTokenCallStarted()
    fun onTokenRecieved(token: Token)
    fun onTokenCallFailed(verifyException: VerifyException)
}