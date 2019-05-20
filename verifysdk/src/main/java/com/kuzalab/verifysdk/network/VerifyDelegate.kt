/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 6:30 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 6:30 PM
 *
 */

import android.content.Context
import com.kuzalab.verifysdk.interfaces.*
import com.kuzalab.verifysdk.models.*
import com.kuzalab.verifysdk.utils.ErrorUtils
import com.kuzalab.verifysdk.utils.FailureUtils
import com.kuzalab.verifysdk.utils.Validator
import com.kuzalab.verifysdk.utils.VerifyConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal open class VerifyDelegate(

    private var context: Context,
    private var enviroment: Enviroment = Enviroment.SANDBOX,
    private var consumerKey: String? = null,
    private var secretKey: String? = null


) {


    private var token: String? = null

    internal fun generateToken(tokenCallListener: TokenCallListener?) {
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
                        tokenCallListener?.onTokenCallFailed(FailureUtils().parseError(call, t))


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
            tokenCallListener?.onTokenCallFailed(VerifyException("Error Authenticating App, \n Consumer Key or Secret Key is invalid or null"))

        }


    }

    internal fun loadVerifyPerson(
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
                    verifyUserDetailsListener.onFailure(FailureUtils().parseError(call, t))

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

    internal fun loadPerson(id: String, userDetailsListener: GetUserDetailsListener): Call<SearchUserResponse>? {
        userDetailsListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            userDetailsListener.onFailure(VerifyException("No internet connection"))
            return null
        }

        val call = RequestService.getService(token!!, getBaseUrl()).searchIdNumber(id)

        GlobalScope.launch(context = Dispatchers.Main) {
            call.enqueue(object : Callback<SearchUserResponse> {
                override fun onFailure(call: Call<SearchUserResponse>?, t: Throwable?) {

                    userDetailsListener.onFailure(FailureUtils().parseError(call, t))


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

    internal fun loadNcaContractor(
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
                    searchNcaContractorByIdListener.onFailure(FailureUtils().parseError(call, t))

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

    internal fun loadNcaContractors(
        s: String,
        searchNcaContractorByNameListener: SearchNcaContractorByNameListener
    ): Call<SearchNcaContractorsResponse>? {
        searchNcaContractorByNameListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            searchNcaContractorByNameListener.onFailure(VerifyException("No internet connection"))
            return null
        }
        val call = RequestService.getService(token!!, getBaseUrl()).searchNcaContractorByName(s)

        GlobalScope.launch(context = Dispatchers.Main) {
            call.enqueue(object : Callback<SearchNcaContractorsResponse> {
                override fun onFailure(call: Call<SearchNcaContractorsResponse>?, t: Throwable?) {

                    searchNcaContractorByNameListener.onFailure(FailureUtils().parseError(call, t))

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

    internal fun loadverifyNcaContractor(
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
                    VerifyNcaContractorListener.onFailure(FailureUtils().parseError(call, t))
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