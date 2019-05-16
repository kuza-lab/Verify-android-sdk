package com.kuzalab.verifysdk.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ParamsResponse {
    @SerializedName("parameter_name")
    @Expose
    var parameterName: String? = null
    @SerializedName("parameter_value")
    @Expose
    var parameterValue: String? = null
    @SerializedName("is_verified")
    @Expose
    var isVerified: Boolean? = null
    @SerializedName("status")
    @Expose
    var status: String? = null

}