/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.verifysdk.models

class VerifyNcaContractor {

    var registration_no: String? = null
    var contractor_name: String? = null
    var town: String? = null
    var category: String? = null
    var contractor_class: String? = null


    constructor(
        registration_no: String? = null,
        contractor_name: String? = null,
        town: String? = null,
        category: String? = null,
        contractor_class: String? = null
    ) {
        this.registration_no = registration_no
        this.contractor_name = contractor_name
        this.town = town
        this.category = category
        this.contractor_class = contractor_class
    }
}