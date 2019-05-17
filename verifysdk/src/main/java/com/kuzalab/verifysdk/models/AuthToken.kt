/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kuzalab.verifysdk.models.Token


class AuthToken {

    @SerializedName("result_code")
    @Expose
    var resultCode: Int? = null
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: Token? = null
    @SerializedName("errors")
    @Expose
    var errors: List<String>? = null


}

