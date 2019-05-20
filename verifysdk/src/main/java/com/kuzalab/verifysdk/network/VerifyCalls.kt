/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 10:23 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 10:21 PM
 *
 */

import android.content.Context
import com.kuzalab.verifysdk.interfaces.*
import com.kuzalab.verifysdk.models.*
import com.kuzalab.verifysdk.utils.CALL_TAGS
import com.kuzalab.verifysdk.utils.Validator
import com.kuzalab.verifysdk.utils.VerifyConstants
import retrofit2.Call

internal open class VerifyCalls(

    context: Context,
    enviroment: Environment = Environment.SANDBOX,
    consumerKey: String? = null,
    secretKey: String? = null


) : VerifyDelegate(context, enviroment, consumerKey, secretKey) {


    val VERIFY_DATE_FORMAT = VerifyConstants().VERIFY_DATE_FORMAT
    val VERIFY_GENDER_ARRAY = VerifyConstants().VERIFY_GENDER_ARRAY

    var callSearchUserResponse: Call<SearchUserResponse>? = null
    var callVerifyPersonResponse: Call<VerifyPersonResponse>? = null
    var callSearchNcaContractorResponse: Call<SearchNcaContractorResponse>? = null
    var callSearchNcaContractorsResponse: Call<SearchNcaContractorsResponse>? = null
    var callVerifyNcaContractorResponse: Call<VerifyNcaContractorResponse>? = null

    fun cancel(tag: String?) {


        when (tag) {
            CALL_TAGS.SEARCH_PERSON.name -> {
                cancel(callSearchUserResponse)
            }
            CALL_TAGS.VERIFY_PERSON.name -> {
                cancel(callVerifyPersonResponse)
            }
            CALL_TAGS.SEARCH_NCA_ID.name -> {
                cancel(callSearchNcaContractorResponse)
            }
            CALL_TAGS.SEARCH_NCA_NAME.name -> {
                cancel(callSearchNcaContractorsResponse)
            }
            CALL_TAGS.VERIFY_NCA.name -> {
                cancel(callVerifyNcaContractorResponse)
            }
        }
    }

    private fun <T> cancel(call: Call<T>?) {
        if (call != null && call.isExecuted && !call.isCanceled) {
            call.cancel()
        }
    }


    private var token: String? = null


    fun getPerson(s: String?, getUserDetailsListener: GetUserDetailsListener): VerifyCall<SearchUserResponse>? {

        if (!Validator().isValidId(s, false)) {
            getUserDetailsListener.onFailure(VerifyException("Id number is invalid"))
            return null
        }


        if (token != null) {
            callSearchUserResponse = loadPerson(s!!, getUserDetailsListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    getUserDetailsListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    callSearchUserResponse = loadPerson(s!!, getUserDetailsListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    getUserDetailsListener.onFailure(verifyException)

                }
            })

        }

        return VerifyCall(callSearchUserResponse)

    }


    fun verifyPerson(verifyPersonModel: VerifyPersonModel, verifyUserDetailsListener: VerifyUserDetailsListener)
            : VerifyCall<VerifyPersonResponse>? {


        val objectVerification = Validator().validatePersonObject(verifyPersonModel)
        if (!objectVerification.isValid) {
            verifyUserDetailsListener.onFailure(VerifyException(objectVerification.reasonInvalid))
            return null
        }

        if (token != null) {

            callVerifyPersonResponse = loadVerifyPerson(verifyPersonModel, verifyUserDetailsListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    verifyUserDetailsListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    callVerifyPersonResponse = loadVerifyPerson(verifyPersonModel, verifyUserDetailsListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    verifyUserDetailsListener.onFailure(verifyException)

                }
            })

        }


        return VerifyCall(callVerifyPersonResponse)
    }



    fun searchNcaContractorById(s: String, searchNcaContractorByIdListener: SearchNcaContractorByIdListener)
            : VerifyCall<SearchNcaContractorResponse>? {


        if (Validator().isNull(s)) {
            searchNcaContractorByIdListener.onFailure(VerifyException("Registration number must be set"))
            return null
        }
        if (token != null) {

            callSearchNcaContractorResponse = loadNcaContractor(s, searchNcaContractorByIdListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    searchNcaContractorByIdListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    callSearchNcaContractorResponse = loadNcaContractor(s, searchNcaContractorByIdListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    searchNcaContractorByIdListener.onFailure(verifyException)

                }
            })

        }

        return VerifyCall(callSearchNcaContractorResponse)

    }



    fun searchNcaContractorByName(s: String?, searchNcaContractorByNameListener: SearchNcaContractorByNameListener)
            : VerifyCall<SearchNcaContractorsResponse>? {
        if (Validator().isNull(s)) {
            searchNcaContractorByNameListener.onFailure(VerifyException("Name cannot be null"))
            return null
        }


        if (token != null) {

            callSearchNcaContractorsResponse = loadNcaContractors(s!!, searchNcaContractorByNameListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    searchNcaContractorByNameListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    callSearchNcaContractorsResponse = loadNcaContractors(s!!, searchNcaContractorByNameListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    searchNcaContractorByNameListener.onFailure(verifyException)

                }
            })

        }

        return VerifyCall(callSearchNcaContractorsResponse)


    }

    fun verifyNcaContractor(
        verifyNcaContractor: VerifyNcaContractor, VerifyNcaContractorListener: VerifyNcaContractorListener
    ): VerifyCall<VerifyNcaContractorResponse>? {


        val objectVerificationModel = Validator().validateNcaObject(verifyNcaContractor)

        if (!objectVerificationModel.isValid) {
            VerifyNcaContractorListener.onFailure(VerifyException(objectVerificationModel.reasonInvalid))
            return null
        }




        if (token != null) {

            callVerifyNcaContractorResponse = loadverifyNcaContractor(verifyNcaContractor, VerifyNcaContractorListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    VerifyNcaContractorListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    callVerifyNcaContractorResponse =
                        loadverifyNcaContractor(verifyNcaContractor, VerifyNcaContractorListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    VerifyNcaContractorListener.onFailure(verifyException)

                }
            })

        }


        return VerifyCall(callVerifyNcaContractorResponse)

    }


}