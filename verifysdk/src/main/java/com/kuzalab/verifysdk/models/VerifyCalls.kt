/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 5:41 PM
 *
 */

import android.content.Context
import com.kuzalab.verifysdk.interfaces.*
import com.kuzalab.verifysdk.models.*
import com.kuzalab.verifysdk.network.VerifyCall
import com.kuzalab.verifysdk.utils.CALL_TAGS
import com.kuzalab.verifysdk.utils.ErrorUtils
import com.kuzalab.verifysdk.utils.Validator
import com.kuzalab.verifysdk.utils.VerifyConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal open class VerifyCalls(

    private var context: Context,
    private var enviroment: Enviroment = Enviroment.SANDBOX,
    private var consumerKey: String? = null,
    private var secretKey: String? = null


) {


    val VERIFY_DATE_FORMAT = VerifyConstants().VERIFY_DATE_FORMAT
    val VERIFY_GENDER_ARRAY = VerifyConstants().VERIFY_GENDER_ARRAY

    var callSearchUserResponse: Call<SearchUserResponse>? = null
    var callVerifyPersonResponse: Call<VerifyPersonResponse>? = null
    var callSearchNcaContractorResponse: Call<SearchNcaContractorResponse>? = null
    var callSearchNcaContractorsResponse: Call<SearchNcaContractorsResponse>? = null
    var callVerifyNcaContractorResponse: Call<VerifyNcaContractorResponse>? = null

    fun cancel(tag: String) {

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

    private fun generateToken(tokenCallListener: TokenCallListener?) {
        tokenCallListener?.onTokenCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            tokenCallListener?.onTokenCallFailed(VerifyException("No internet connection"))
            return
        }
        if (enviroment == null) {
            enviroment = Enviroment.SANDBOX
        }

        if (enviroment == Enviroment.SANDBOX) {
            consumerKey = VerifyConstants().consumerKey
            secretKey = VerifyConstants().consumerSecret
        }

        if (!Validator().isNull(consumerKey) && !Validator().isNull(secretKey)) {
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService(consumerKey!!, secretKey!!, getBaseUrl()).generateToken()
                call.enqueue(object : Callback<AuthToken> {
                    override fun onFailure(call: Call<AuthToken>?, t: Throwable?) {
                        tokenCallListener?.onTokenCallFailed(VerifyException("" + t))

                    }

                    override fun onResponse(call: Call<AuthToken>?, response: Response<AuthToken>?) {

                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success == true && response.body()?.data != null) {
                                    token = response.body()?.data?.token
                                    tokenCallListener?.onTokenRecieved(response.body()?.data!!)
                                } else {
                                    tokenCallListener?.onTokenCallFailed(
                                        VerifyException(
                                            response.body()?.message,
                                            "",
                                            response.body()?.errors
                                        )
                                    )

                                }
                            } else {

                                tokenCallListener?.onTokenCallFailed(ErrorUtils().parseError(response))

                            }
                        } else {
                            tokenCallListener?.onTokenCallFailed(VerifyException("Could Not Fetch Token"))

                        }

                    }
                })
            }

        } else {
            tokenCallListener?.onTokenCallFailed(VerifyException("Error Authentication App. Consumer Key or Secret Key is invalid or null"))

        }


    }

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

    private fun loadVerifyPerson(
        verifyPersonModel: VerifyPersonModel,
        verifyUserDetailsListener: VerifyUserDetailsListener
    ): Call<VerifyPersonResponse>? {

        verifyUserDetailsListener.onCallStarted()
        if (!NetworkUtils.isConnected(context)) {
            verifyUserDetailsListener.onFailure(VerifyException("No internet connection"))
            return null
        }
        val call = RequestService.getService(token!!, getBaseUrl())
            .verifyPerson(verifyPersonModel.id_number!!, verifyPersonModel)

        GlobalScope.launch(context = Dispatchers.Main) {
            call.enqueue(object : Callback<VerifyPersonResponse> {
                override fun onFailure(call: Call<VerifyPersonResponse>?, t: Throwable?) {
                    verifyUserDetailsListener.onFailure(VerifyException("" + t))

                }

                override fun onResponse(call: Call<VerifyPersonResponse>?, response: Response<VerifyPersonResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success == true && response.body()?.data != null) {
                                verifyUserDetailsListener.onResponse(response.body()?.data as List<ParamsResponse>)
                            } else {
                                verifyUserDetailsListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        response.body()?.statusName,
                                        response.body()?.errors
                                    )
                                )

                            }
                        } else {
                            verifyUserDetailsListener.onFailure(ErrorUtils().parseError(response))

                        }
                    } else {
                        verifyUserDetailsListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })
        }

        return call
    }

    private fun loadPerson(id: String, userDetailsListener: GetUserDetailsListener): Call<SearchUserResponse>? {
        userDetailsListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            userDetailsListener.onFailure(VerifyException("No internet connection"))
            return null
        }

        val call = RequestService.getService(token!!, getBaseUrl()).searchIdNumber(id)

        GlobalScope.launch(context = Dispatchers.Main) {
            call.enqueue(object : Callback<SearchUserResponse> {
                override fun onFailure(call: Call<SearchUserResponse>?, t: Throwable?) {
                    userDetailsListener.onFailure(VerifyException("" + t))

                }

                override fun onResponse(call: Call<SearchUserResponse>?, response: Response<SearchUserResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success == true && response.body()?.data != null) {
                                userDetailsListener.onResponse(response.body()?.data as Person)
                            } else {
                                userDetailsListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        response.body()?.message,
                                        response.body()?.errors
                                    )
                                )

                            }
                        } else {
                            userDetailsListener.onFailure(ErrorUtils().parseError(response))

                        }
                    } else {
                        userDetailsListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })
        }

        return call

    }

    private fun getBaseUrl(): String {

        var baseUrls = BaseUrls().LIVE
        when (enviroment) {
            Enviroment.PRODUCTION -> {
                baseUrls = BaseUrls().LIVE

            }
            Enviroment.SANDBOX -> {
                baseUrls = BaseUrls().SANDBOX

            }
        }
        return baseUrls
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

    private fun loadNcaContractor(
        s: String,
        searchNcaContractorByIdListener: SearchNcaContractorByIdListener
    ): Call<SearchNcaContractorResponse>? {
        searchNcaContractorByIdListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            searchNcaContractorByIdListener.onFailure(VerifyException("No internet connection"))
            return null
        }
        val call = RequestService.getService(token!!, getBaseUrl()).searchNcaContractorById(s)

        GlobalScope.launch(context = Dispatchers.Main) {
            call.enqueue(object : Callback<SearchNcaContractorResponse> {
                override fun onFailure(call: Call<SearchNcaContractorResponse>?, t: Throwable?) {
                    searchNcaContractorByIdListener.onFailure(VerifyException("" + t?.message))

                }

                override fun onResponse(
                    call: Call<SearchNcaContractorResponse>?, response: Response<SearchNcaContractorResponse>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {


                            if (response.body() != null) {

                                if (response.body()?.data != null && response.body()?.success == true) {
                                    searchNcaContractorByIdListener.onResponse(
                                        response.body()?.data as NcaContractor
                                    )
                                } else {
                                    searchNcaContractorByIdListener.onFailure(
                                        VerifyException(
                                            response.body()?.message,
                                            response.body()?.message,
                                            response.body()?.errors
                                        )
                                    )
                                }

                            } else {
                                searchNcaContractorByIdListener.onFailure(
                                    VerifyException(
                                        response.message()
                                    )
                                )


                            }


                        } else {
                            searchNcaContractorByIdListener.onFailure(ErrorUtils().parseError(response))

                        }
                    } else {
                        searchNcaContractorByIdListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })
        }

        return call

    }

    private fun loadNcaContractors(s: String, searchNcaContractorByNameListener: SearchNcaContractorByNameListener):
            Call<SearchNcaContractorsResponse>? {
        searchNcaContractorByNameListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            searchNcaContractorByNameListener.onFailure(VerifyException("No internet connection"))
            return null
        }
        val call = RequestService.getService(token!!, getBaseUrl()).searchNcaContractorByName(s)

        GlobalScope.launch(context = Dispatchers.Main) {
            call.enqueue(object : Callback<SearchNcaContractorsResponse> {
                override fun onFailure(call: Call<SearchNcaContractorsResponse>?, t: Throwable?) {

                    searchNcaContractorByNameListener.onFailure(VerifyException("" + t?.message))

                }

                override fun onResponse(
                    call: Call<SearchNcaContractorsResponse>?,
                    response: Response<SearchNcaContractorsResponse>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {

                            if (response.body()?.success == true && response.body()?.data != null) {
                                searchNcaContractorByNameListener.onResponse(response.body()?.data!!)
                            } else {
                                searchNcaContractorByNameListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        response.body()?.message,
                                        response.body()?.errors
                                    )
                                )

                            }
                        } else {
                            searchNcaContractorByNameListener.onFailure(ErrorUtils().parseError(response))

                        }
                    } else {
                        searchNcaContractorByNameListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })


        }
        return call


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

    private fun loadverifyNcaContractor(
        verifyNcaContractor: VerifyNcaContractor, VerifyNcaContractorListener: VerifyNcaContractorListener
    ): Call<VerifyNcaContractorResponse>? {
        VerifyNcaContractorListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            VerifyNcaContractorListener.onFailure(VerifyException("No internet connection"))
            return null
        }
        val call = RequestService.getService(token!!, getBaseUrl())
            .verifyNcaContractor(verifyNcaContractor.registration_no!!, verifyNcaContractor)

        GlobalScope.launch(context = Dispatchers.Main) {
            call.enqueue(object : Callback<VerifyNcaContractorResponse> {
                override fun onFailure(call: Call<VerifyNcaContractorResponse>?, t: Throwable?) {
                    VerifyNcaContractorListener.onFailure(VerifyException("" + t))
                }

                override fun onResponse(
                    call: Call<VerifyNcaContractorResponse>?,
                    response: Response<VerifyNcaContractorResponse>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success == true && response.body()?.data != null) {
                                VerifyNcaContractorListener.onResponse(response.body()?.data!!)
                            } else {
                                VerifyNcaContractorListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        "",
                                        response.body()?.errors
                                    )
                                )

                            }
                        } else {
                            VerifyNcaContractorListener.onFailure(ErrorUtils().parseError(response))

                        }
                    } else {
                        VerifyNcaContractorListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })
        }

        return call
    }


}