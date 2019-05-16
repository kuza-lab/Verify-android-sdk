package com.kuzalab.verifysdk.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Person {


    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = ""

    @SerializedName("status")
    @Expose
    var status: String? = ""
    @SerializedName("verified")
    @Expose
    var verified: Boolean? = null

    @SerializedName("citizenship")
    @Expose
    var citizenship: String? = null
    @SerializedName("clan")
    @Expose
    var clan: String? = null
    @SerializedName("date_of_birth")
    @Expose
    var dateOfBirth: String? = null
    @SerializedName("date_of_death")
    @Expose
    var dateOfDeath: String? = null
    @SerializedName("gender")
    @Expose
    var gender: String? = null
    @SerializedName("ethnic_group")
    @Expose
    var ethnicGroup: String? = null
    @SerializedName("family")
    @Expose
    var family: String? = null
    @SerializedName("fingerprint")
    @Expose
    var fingerprint: String? = null
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null
    @SerializedName("other_name")
    @Expose
    var otherName: String? = null
    @SerializedName("surname")
    @Expose
    var surname: String? = null
    @SerializedName("id_number")
    @Expose
    var idNumber: String? = null
    @SerializedName("occupation")
    @Expose
    var occupation: String? = null
    @SerializedName("photo")
    @Expose
    var photo: String? = null
    @SerializedName("pin")
    @Expose
    var pin: String? = null
    @SerializedName("place_of_birth")
    @Expose
    var placeOfBirth: String? = null
    @SerializedName("place_of_death")
    @Expose
    var placeOfDeath: String? = null
    @SerializedName("place_of_live")
    @Expose
    var placeOfLive: String? = null
    @SerializedName("signature")
    @Expose
    var signature: String? = null
    @SerializedName("date_of_issue")
    @Expose
    var dateOfIssue: String? = null
    @SerializedName("reg_office")
    @Expose
    var regOffice: String? = null
    @SerializedName("serial_number")
    @Expose
    var serialNumber: String? = null
    @SerializedName("full_name")
    @Expose
    var fullName: String? = null
    @SerializedName("is_alive")
    @Expose
    var isAlive: Boolean? = null

}