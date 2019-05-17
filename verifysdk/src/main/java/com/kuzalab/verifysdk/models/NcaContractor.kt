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


class NcaContractor {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("registration_no")
    @Expose
    var registrationNo: String? = null
    @SerializedName("contractor_name")
    @Expose
    var contractorName: String? = null
    @SerializedName("town")
    @Expose
    var town: String? = null
    @SerializedName("category")
    @Expose
    var category: String? = null
    @SerializedName("class")
    @Expose
    var _class: String? = null

    @SerializedName("category_class")
    @Expose
    var category_class: String? = null


    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("verified")
    @Expose
    var verified: Boolean? = null


}