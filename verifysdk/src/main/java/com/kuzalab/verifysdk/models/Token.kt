/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 3:58 PM
 *
 */

package com.kuzalab.verifysdk.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class Token {

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("expires_after")
    @Expose
    var expiresIn: String? = null


}