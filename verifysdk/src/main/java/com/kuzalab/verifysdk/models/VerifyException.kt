package com.kuzalab.verifysdk.models

class VerifyException : Exception {

    var errorType: String? = null
    var errorMessage: String? = null
    var errors: List<String>? = null

    constructor (errorMessage: String?) : super(errorMessage) {
        this.errorMessage = errorMessage

    }


    constructor(errorType: String?, errorMessage: String?) : super(errorMessage) {

        this.errorType = errorType
        this.errorMessage = errorMessage
    }

    constructor(errorType: String?, errorMessage: String?, errors: List<String>?) : super(errorMessage) {

        this.errorType = errorType
        this.errorMessage = errorMessage
        this.errors = errors
    }


}