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


class SearchNcaContractorResponse {
    @SerializedName("status_code")
    @Expose
    val statusCode: Int? = null
    @SerializedName("status_name")
    @Expose
    val statusName: String? = null
    @SerializedName("success")
    @Expose
    val success: Boolean? = null
    @SerializedName("message")
    @Expose
    val message: String? = null
    @SerializedName("result_code")
    @Expose
    val resultCode: Int? = null
    @SerializedName("data")
    @Expose
    val data: NcaContractor? = null
    @SerializedName("errors")
    @Expose
    val errors: List<String>? = null
    @SerializedName("total_records")
    @Expose
    val totalRecords: Int? = null


}