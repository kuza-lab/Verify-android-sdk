/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 3:58 PM
 *
 */

package com.kuzalab.verifysdk.interfaces

import com.kuzalab.verifysdk.models.Token
import com.kuzalab.verifysdk.models.VerifyException

internal interface TokenCallListener {
    fun onTokenCallStarted()
    fun onTokenRecieved(token: Token)
    fun onTokenCallFailed(verifyException: VerifyException)
}