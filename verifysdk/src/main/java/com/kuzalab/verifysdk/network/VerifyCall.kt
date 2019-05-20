/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 5:41 PM
 *
 */

package com.kuzalab.verifysdk.network

import retrofit2.Call

internal class VerifyCall<T> {
    private var call: Call<T>? = null

    constructor(call: Call<T>?) {
        this.call = call
    }


    fun cancel() {
        if (call != null && (call as Call<T>).isExecuted && !(call as Call<T>).isCanceled) {
            (call as Call<T>).cancel()
        }
    }

    fun isCanceled(): Boolean? {

        return call?.isCanceled
    }

    fun isExecuted(): Boolean? {

        return call?.isExecuted
    }


}