/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.verifysdk.interfaces

import com.kuzalab.verifysdk.models.Token
import com.kuzalab.verifysdk.models.VerifyException

interface TokenCallListener {
    fun onTokenCallStarted()
    fun onTokenRecieved(token: Token)
    fun onTokenCallFailed(verifyException: VerifyException)
}