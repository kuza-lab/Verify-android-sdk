package com.kuzalab.verifysdk.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class VerifyNcaContractorResponse {
    @SerializedName("status_code")
    @Expose
    var statusCode: Int? = null
    @SerializedName("status_name")
    @Expose
    var statusName: String? = null
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("result_code")
    @Expose
    var resultCode: Int? = null
    @SerializedName("data")
    @Expose
    var data: List<ParamsResponse>? = null

    @SerializedName("errors")
    @Expose
    var errors: List<String>? = null
    @SerializedName("total_records")
    @Expose
    var totalRecords: Int? = null
}