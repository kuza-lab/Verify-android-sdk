package com.kuzalab.verifysdk.models

class ErrorBody {

    var status_code: Int? = 0
    var status_name: String? = "Error"
    var success: Boolean? = false
    var message: String? = "Error"
    var result_code: Int? = 0
    var errors: List<String>? = null

}