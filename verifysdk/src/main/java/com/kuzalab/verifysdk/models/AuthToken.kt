/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 3:58 PM
 *
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kuzalab.verifysdk.models.Token


internal class AuthToken {

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

