package com.kuzalab.verifysdk

import android.util.Patterns
import com.kuzalab.verifysdk.data.VerifyConstants
import com.kuzalab.verifysdk.models.ObjectVerificationModel
import com.kuzalab.verifysdk.models.VerifyNcaContractor
import com.kuzalab.verifysdk.models.VerifyPersonodel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal class Validator {

    fun isNull(s: String?): Boolean {
        if (s == null) {
            return true
        }
        if (s == "") {
            return true
        }
        return false
    }


    private fun isValidDate(date: String): Boolean {
        val format = SimpleDateFormat(VerifyConstants().VERIFY_DATE_FORMAT, Locale.US)
        format.isLenient = false
        try {
            format.parse(date)
            return true
        } catch (e: ParseException) {

            return false

        }
    }

    private fun isValidGender(gender: String): Boolean {
        return gender == VerifyConstants().VERIFY_GENDER_ARRAY[0] || gender == VerifyConstants().VERIFY_GENDER_ARRAY[1]

    }

    private fun isValidPhoneNumber(mobile: String): Boolean {
        val regEx = "^[0-9]{9}$"
        return mobile.matches(regEx.toRegex())
    }

    private fun isValidEmail(email: String): Boolean {


        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidName(name: String): Boolean {

        return name.trim().length > 2
    }

    fun isValidId(id_number: String): Boolean {

        return id_number.trim().length > 7

    }

    fun isValidDateOfBirth(dateOfBirth: String?, nullable: Boolean): Boolean {


        if (isNull(dateOfBirth)) {

            return nullable

        } else {
            return isValidDate(dateOfBirth!!)
        }


    }

    fun isValidGender(gender: String?, nullable: Boolean): Boolean {

        if (isNull(gender)) {

            return nullable

        } else {
            return isValidGender(gender!!)
        }
    }

    fun isValidPhoneNumber(mobile: String?, nullable: Boolean): Boolean {
        if (isNull(mobile)) {

            return nullable

        } else {
            return isValidPhoneNumber(mobile!!)
        }
    }

    fun isValidEmail(email: String, nullable: Boolean): Boolean {

        if (isNull(email)) {

            return nullable

        } else {
            return isValidEmail(email)
        }
    }

    fun isValidName(name: String, nullable: Boolean): Boolean {

        if (isNull(name)) {

            return nullable

        } else {
            return isValidName(name)
        }
    }

    fun isValidId(id_number: String?, nullable: Boolean): Boolean {

        if (isNull(id_number)) {

            return nullable

        } else {
            return isValidId(id_number!!)
        }
    }


    fun validatePersonObject(verifyPersonModel: VerifyPersonodel): ObjectVerificationModel {
        val objectVerificationModel = ObjectVerificationModel(true, "Default", "Default")


        if (isNull(verifyPersonModel.first_name)
            && isNull(verifyPersonModel.surname)
            && isNull(verifyPersonModel.other_name)
            && isNull(verifyPersonModel.gender)
            && isNull(verifyPersonModel.citizenship)
            && isNull(verifyPersonModel.date_of_birth)
            && isNull(verifyPersonModel.phone_number)
            && isNull(verifyPersonModel.id_number)
            && isNull(verifyPersonModel.serial_number)
        ) {

            objectVerificationModel.isValid = false
            objectVerificationModel.invalidField = "Multiple"
            objectVerificationModel.reasonInvalid =
                "Null fields error , All Fields are Invalid or Null . Atleast One field is required "


            return objectVerificationModel


        } else {


            if (isValidPhoneNumber(verifyPersonModel.phone_number, false) || isValidId(
                    verifyPersonModel.id_number,
                    false
                )
            ) {

                if (!isValidGender(verifyPersonModel.gender, nullable = true)) {

                    objectVerificationModel.isValid = false
                    objectVerificationModel.invalidField = "Gender"
                    objectVerificationModel.reasonInvalid =
                        "The Entry for Gender is Invalid . Please refer to the documentation for correct implementation "
                    return objectVerificationModel
                }
                if (!isValidDateOfBirth(verifyPersonModel.date_of_birth, nullable = true)) {

                    objectVerificationModel.isValid = false
                    objectVerificationModel.invalidField = "Date Of Birth"
                    objectVerificationModel.reasonInvalid =
                        "The Entry for Date Of Birth is Invalid . Please refer to the documentation for correct implementation "
                    return objectVerificationModel
                }

                objectVerificationModel.isValid = true


            } else {

                objectVerificationModel.isValid = false
                objectVerificationModel.invalidField = "Id Number and Phone Number"
                objectVerificationModel.reasonInvalid =
                    "Both ID Number and Phone Number are Invalid . Either ID Number or Phone must be filled "

                return objectVerificationModel
            }
        }

        return objectVerificationModel

    }

    fun validateNcaObject(verifyNcaContractor: VerifyNcaContractor): ObjectVerificationModel {
        val objectVerificationModel = ObjectVerificationModel(true, "", "")

        if (isNull(verifyNcaContractor.registration_no)
            && isNull(verifyNcaContractor.contractor_name)
            && isNull(verifyNcaContractor.town)
            && isNull(verifyNcaContractor.category)
            && isNull(verifyNcaContractor.contractor_class)
        ) {

            objectVerificationModel.isValid = false
            objectVerificationModel.invalidField = "Multiple"
            objectVerificationModel.reasonInvalid =
                "Null fields error , All Fields are Invalid or Null . Atleast One field is required"

            return objectVerificationModel
        }




        if (isNull(verifyNcaContractor.registration_no)) {
            objectVerificationModel.isValid = false
            objectVerificationModel.invalidField = "Registration Number"
            objectVerificationModel.reasonInvalid =
                "Registration number is null or invalid . The field Registration number must be filled "
        }

        objectVerificationModel.isValid = true
        return objectVerificationModel

    }


}