/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.verifysdk.models

class ErrorBody {

    var status_code: Int? = 0
    var status_name: String? = "Error"
    var success: Boolean? = false
    var message: String? = "Error"
    var result_code: Int? = 0
    var errors: List<String>? = null

}