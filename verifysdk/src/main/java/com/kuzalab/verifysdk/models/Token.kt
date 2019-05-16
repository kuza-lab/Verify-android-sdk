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