import android.content.Context
import com.kuzalab.verifysdk.ErrorUtils
import com.kuzalab.verifysdk.Validator
import com.kuzalab.verifysdk.data.VerifyConstants
import com.kuzalab.verifysdk.interfaces.*
import com.kuzalab.verifysdk.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Verify(

    private val context: Context,
    private val enviroment: Enviroment = Enviroment.PRODUCTION,
    private val consumerKey: String? = null,
    private val secretKey: String? = null


) {
    val VERIFY_DATE_FORMAT = VerifyConstants().VERIFY_DATE_FORMAT
    val VERIFY_GENDER_ARRAY = VerifyConstants().VERIFY_GENDER_ARRAY

    private var token: String? = null

    fun generateToken(tokenCallListener: TokenCallListener?) {
        tokenCallListener?.onTokenCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            tokenCallListener?.onTokenCallFailed(VerifyException("No internet connection"))
            return
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

    fun getPerson(s: String?, getUserDetailsListener: GetUserDetailsListener) {

        if (Validator().isNull(s)) {
            getUserDetailsListener.onFailure(VerifyException("Id number cannot be null"))
            return
        }


        if (token != null) {
            loadPerson(s!!, getUserDetailsListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    getUserDetailsListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    loadPerson(s!!, getUserDetailsListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    getUserDetailsListener.onFailure(verifyException)

                }
            })

        }


    }


    fun verifyPerson(verifyPersonModel: VerifyPersonodel, verifyUserDetailsListener: VerifyUserDetailsListener) {


        val objectVerification = Validator().validatePersonObject(verifyPersonModel)
        if (!objectVerification.isValid) {
            verifyUserDetailsListener.onFailure(VerifyException(objectVerification.reasonInvalid))
            return
        }






        if (token != null) {

            loadVerifyPerson(verifyPersonModel, verifyUserDetailsListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    verifyUserDetailsListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    loadVerifyPerson(verifyPersonModel, verifyUserDetailsListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    verifyUserDetailsListener.onFailure(verifyException)

                }
            })

        }
            
        

    }

    private fun loadVerifyPerson(
        verifyPersonModel: VerifyPersonodel,
        verifyUserDetailsListener: VerifyUserDetailsListener
    ) {

        verifyUserDetailsListener.onCallStarted()
        if (!NetworkUtils.isConnected(context)) {
            verifyUserDetailsListener.onFailure(VerifyException("No internet connection"))
            return
        }
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl())
                .verifyPerson(verifyPersonModel.id_number!!, verifyPersonModel)
            call.enqueue(object : Callback<VerifyPersonResponse> {
                override fun onFailure(call: Call<VerifyPersonResponse>?, t: Throwable?) {
                    verifyUserDetailsListener.onFailure(VerifyException("" + t))

                }

                override fun onResponse(call: Call<VerifyPersonResponse>?, response: Response<VerifyPersonResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success == true && response.body()?.data != null) {
                                verifyUserDetailsListener.onResponse(response.body()?.data!!)
                            } else {
                                verifyUserDetailsListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        "",
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

    }

    private fun loadPerson(id: String, userDetailsListener: GetUserDetailsListener) {
        userDetailsListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            userDetailsListener.onFailure(VerifyException("No internet connection"))
            return
        }

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl()).searchIdNumber(id)
            call.enqueue(object : Callback<SearchUserResponse> {
                override fun onFailure(call: Call<SearchUserResponse>?, t: Throwable?) {
                    userDetailsListener.onFailure(VerifyException("" + t))

                }

                override fun onResponse(call: Call<SearchUserResponse>?, response: Response<SearchUserResponse>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success == true && response.body()?.data != null) {
                                userDetailsListener.onResponse(response.body()?.data!!)
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

    fun searchNcaContractorById(s: String, searchNcaContractorByIdListener: SearchNcaContractorByIdListener) {

        if (Validator().isNull(s)) {
            searchNcaContractorByIdListener.onFailure(VerifyException("Registration number must be set"))
            return
        }
        if (token != null) {

            loadNcaContractor(s, searchNcaContractorByIdListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    searchNcaContractorByIdListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    loadNcaContractor(s, searchNcaContractorByIdListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    searchNcaContractorByIdListener.onFailure(verifyException)

                }
            })

        }


    }

    private fun loadNcaContractor(s: String, searchNcaContractorByIdListener: SearchNcaContractorByIdListener) {
        searchNcaContractorByIdListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            searchNcaContractorByIdListener.onFailure(VerifyException("No internet connection"))
            return
        }

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl()).searchNcaContractorById(s)
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


    }

    private fun loadNcaContractors(s: String, searchNcaContractorByNameListener: SearchNcaContractorByNameListener) {
        searchNcaContractorByNameListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            searchNcaContractorByNameListener.onFailure(VerifyException("No internet connection"))
            return
        }

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl()).searchNcaContractorByName(s)
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


    }

    fun searchNcaContractorByName(s: String?, searchNcaContractorByNameListener: SearchNcaContractorByNameListener) {
        if (Validator().isNull(s)) {
            searchNcaContractorByNameListener.onFailure(VerifyException("Name cannot be null"))
            return
        }


        if (token != null) {

            loadNcaContractors(s!!, searchNcaContractorByNameListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    searchNcaContractorByNameListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    loadNcaContractors(s!!, searchNcaContractorByNameListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    searchNcaContractorByNameListener.onFailure(verifyException)

                }
            })

        }


    }

    fun verifyNcaContractor(
        verifyNcaContractor: VerifyNcaContractor, verifyNcaContractorListener: verifyNcaContractorListener
    ) {


        val objectVerificationModel = Validator().validateNcaObject(verifyNcaContractor)

        if (!objectVerificationModel.isValid) {
            verifyNcaContractorListener.onFailure(VerifyException(objectVerificationModel.reasonInvalid))
            return
        }




        if (token != null) {

            loadverifyNcaContractor(verifyNcaContractor, verifyNcaContractorListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    verifyNcaContractorListener.onCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    loadverifyNcaContractor(verifyNcaContractor, verifyNcaContractorListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    verifyNcaContractorListener.onFailure(verifyException)

                }
            })

        }

    }

    private fun loadverifyNcaContractor(
        verifyNcaContractor: VerifyNcaContractor, verifyNcaContractorListener: verifyNcaContractorListener
    ) {
        verifyNcaContractorListener.onCallStarted()

        if (!NetworkUtils.isConnected(context)) {
            verifyNcaContractorListener.onFailure(VerifyException("No internet connection"))
            return
        }
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl())
                .verifyNcaContractor(verifyNcaContractor.registration_no!!, verifyNcaContractor)
            call.enqueue(object : Callback<VerifyNcaContractorResponse> {
                override fun onFailure(call: Call<VerifyNcaContractorResponse>?, t: Throwable?) {
                    verifyNcaContractorListener.onFailure(VerifyException("" + t))
                }
                override fun onResponse(
                    call: Call<VerifyNcaContractorResponse>?,
                    response: Response<VerifyNcaContractorResponse>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success == true && response.body()?.data != null) {
                                verifyNcaContractorListener.onResponse(response.body()?.data!!)
                            } else {
                                verifyNcaContractorListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        "",
                                        response.body()?.errors
                                    )
                                )

                            }
                        } else {
                            verifyNcaContractorListener.onFailure(ErrorUtils().parseError(response))

                        }
                    } else {
                        verifyNcaContractorListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })
        }

    }

    data class Builder(
        var context: Context,
        var enviroment: Enviroment = Enviroment.PRODUCTION,
        var consumerKey: String? = null,
        var secretKey: String? = null
    ) {

        fun enviroment(enviroment: Enviroment) = apply { this.enviroment = enviroment }
        fun consumerKey(consumerKey: String?) = apply { this.consumerKey = consumerKey }
        fun secretKey(secretKey: String) = apply { this.secretKey = secretKey }


        fun build() = Verify(context, enviroment, consumerKey, secretKey)


    }


}