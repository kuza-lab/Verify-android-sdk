/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.verifysdk.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Token {

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("expires_after")
    @Expose
    var expiresIn: String? = null


}