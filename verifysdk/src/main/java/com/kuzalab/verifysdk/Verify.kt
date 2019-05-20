/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 6:30 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 6:30 PM
 *
 */

import android.content.Context
import com.kuzalab.verifysdk.interfaces.*
import com.kuzalab.verifysdk.models.VerifyNcaContractor
import com.kuzalab.verifysdk.models.VerifyPersonModel
import com.kuzalab.verifysdk.utils.CALL_TAGS
import com.kuzalab.verifysdk.utils.Validator
import org.jetbrains.annotations.TestOnly

class Verify(

    context: Context,
    enviroment: Enviroment = Enviroment.SANDBOX,
    consumerKey: String? = null,
    secretKey: String? = null


) {
    private val v: VerifyCalls = VerifyCalls(context, enviroment, consumerKey, secretKey)
    val VERIFY_GENDER_ARRAY = v.VERIFY_GENDER_ARRAY
    val VERIFY_DATE_FORMAT: String? = v.VERIFY_DATE_FORMAT


    @TestOnly
    fun cancel(tag: String?) {
        if (!Validator().isNull(tag)) {
            v.cancel(tag)
        }
    }


    fun getPerson(personId: String, getUserDetailsListener: GetUserDetailsListener): String {
        v.getPerson(personId, getUserDetailsListener)
        return CALL_TAGS.SEARCH_PERSON.name
    }

    fun verifyPerson(
        verifyPersonModel: VerifyPersonModel,
        verifyUserDetailsListener: VerifyUserDetailsListener
    ): String {
        v.verifyPerson(verifyPersonModel, verifyUserDetailsListener)
        return CALL_TAGS.VERIFY_PERSON.name
    }

    fun searchNcaContractorById(
        contractorRegId: String,
        searchNcaContractorByIdListener: SearchNcaContractorByIdListener
    ): String {
        v.searchNcaContractorById(contractorRegId, searchNcaContractorByIdListener)
        return CALL_TAGS.SEARCH_NCA_ID.name
    }

    fun searchNcaContractorByName(
        contractorName: String,
        searchNcaContractorByNameListener: SearchNcaContractorByNameListener
    ): String {

        v.searchNcaContractorByName(contractorName, searchNcaContractorByNameListener)
        return CALL_TAGS.SEARCH_NCA_NAME.name
    }

    fun verifyNcaContractor(
        verifyNcaContractor: VerifyNcaContractor,
        verifyNcaContractorListener: VerifyNcaContractorListener
    ): String {

        v.verifyNcaContractor(verifyNcaContractor, verifyNcaContractorListener)
        return CALL_TAGS.VERIFY_NCA.name
    }

    data class Builder(
        var context: Context,
        var enviroment: Enviroment = Enviroment.PRODUCTION,
        var consumerKey: String? = null,
        var secretKey: String? = null
    ) {

        fun enviroment(enviroment: Enviroment) = apply {

            this.enviroment = enviroment

        }

        fun consumerKey(consumerKey: String?) = apply {
            this.consumerKey = consumerKey

        }

        fun secretKey(secretKey: String?) = apply {
            this.secretKey = secretKey


        }
        fun build() = Verify(context, enviroment, consumerKey, secretKey)


    }


}