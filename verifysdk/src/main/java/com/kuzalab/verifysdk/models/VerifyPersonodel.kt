/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.kuzalab.verifysdk.models

class VerifyPersonodel {

    var id_number: String? = null
    var first_name: String? = null
    var surname: String? = null
    var other_name: String? = null
    var citizenship: String? = null
    var date_of_birth: String? = null
    var gender: String? = null
    var phone_number: String? = null
    var serial_number: String? = null


    constructor(
        id_number: String? = null,
        first_name: String? = null,
        surname: String? = null,
        other_name: String? = null,
        citizenship: String? = null,
        date_of_birth: String? = null,
        gender: String? = null,
        phone_number: String? = null,
        serial_number: String? = null
    ) {
        this.id_number = id_number
        this.first_name = first_name
        this.surname = surname
        this.other_name = other_name
        this.citizenship = citizenship
        this.date_of_birth = date_of_birth
        this.gender = gender
        this.phone_number = phone_number
        this.serial_number = serial_number
    }
}