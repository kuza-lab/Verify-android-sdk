/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 10:23 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 6:30 PM
 *
 */

package com.kuzalab.verifysdk.models

import retrofit2.Call

internal class VerifyCall<T> {
    private var call: Call<T>? = null

    constructor(call: Call<T>?) {
        this.call = call
    }



}