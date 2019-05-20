/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 3:58 PM
 *
 */

package com.kuzalab.verifysdk.models

internal class ObjectVerificationModel {
    constructor(b: Boolean, s: String, s1: String) {
        this.isValid = b
        this.invalidField = s
        this.reasonInvalid = s1
    }

    var isValid: Boolean = false
    var invalidField: String? = null
    var reasonInvalid: String? = null


}