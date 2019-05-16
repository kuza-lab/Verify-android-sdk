import android.content.Context
import android.util.Log
import com.google.gson.Gson
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
    private val secretKey: String? = null,
    private val handleTokenInternally: Boolean? = true,
    private val tokenCallListener: TokenCallListener? = null


) {
    private var token: String? = null


    private fun generateToken(tokenCallListener: TokenCallListener?) {
        if (!NetworkUtils.isConnected(context)) {
            tokenCallListener?.onTokenCallFailed(VerifyException("No internet connection"))
            return
        }
        if (consumerKey != null && secretKey != null) {
            tokenCallListener?.onTokenCallStarted()

            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService(consumerKey, secretKey, getBaseUrl()).generateToken()
                call.enqueue(object : Callback<AuthToken> {
                    override fun onFailure(call: Call<AuthToken>?, t: Throwable?) {
                        Log.d("VerifySdkErrorLog", t.toString())

                        tokenCallListener?.onTokenCallFailed(VerifyException("" + t))

                    }

                    override fun onResponse(call: Call<AuthToken>?, response: Response<AuthToken>?) {
                        Log.d("VerifySdkErrorLog", response.toString())

                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success!!) {
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
                                tokenCallListener?.onTokenCallFailed(VerifyException(response.message()))

                            }
                        } else {
                            tokenCallListener?.onTokenCallFailed(VerifyException("Could Not Fetch Token"))

                        }

                    }
                })
            }

        }


    }


    fun getPerson(s: String, getUserDetailsListener: GetUserDetailsListener) {

        if (s == null) {
            getUserDetailsListener.onFailure(VerifyException("Id number cannot be null"))
            return
        }
        if (getUserDetailsListener == null) {
            getUserDetailsListener.onFailure(VerifyException("Not listener set"))
            return
        }


        if (token != null) {

            loadPerson(s, getUserDetailsListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    tokenCallListener?.onTokenCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    tokenCallListener?.onTokenRecieved(token)
                    loadPerson(s, getUserDetailsListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    tokenCallListener?.onTokenCallFailed(verifyException)

                }
            })

        }


    }

    fun verifyPerson(verifyPersonModel: VerifyPersonodel, verifyUserDetailsListener: VerifyUserDetailsListener) {

        if (verifyPersonModel.first_name == null
            && verifyPersonModel.surname == null
            && verifyPersonModel.other_name == null
            && verifyPersonModel.gender == null
            && verifyPersonModel.citizenship == null
            && verifyPersonModel.date_of_birth == null
            && verifyPersonModel.phone_number == null
            && verifyPersonModel.id_number == null
            && verifyPersonModel.serial_number == null


        ) {
            verifyUserDetailsListener.onFailure(VerifyException("Must have atleast one field not null"))
            return
        }

        if (token != null) {

            loadVerifyPerson(verifyPersonModel, verifyUserDetailsListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    tokenCallListener?.onTokenCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    tokenCallListener?.onTokenRecieved(token)
                    loadVerifyPerson(verifyPersonModel, verifyUserDetailsListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    tokenCallListener?.onTokenCallFailed(verifyException)

                }
            })

        }

    }

    fun loadVerifyPerson(verifyPersonModel: VerifyPersonodel, verifyUserDetailsListener: VerifyUserDetailsListener) {
        if (!NetworkUtils.isConnected(context)) {
            verifyUserDetailsListener.onFailure(VerifyException("No internet connection"))
            return
        }
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl())
                .verifyPerson(verifyPersonModel.id_number!!, verifyPersonModel)
            call.enqueue(object : Callback<VerifyPersonResponse> {
                override fun onFailure(call: Call<VerifyPersonResponse>?, t: Throwable?) {
                    Log.d("VerifySdkErrorLog", "" + t)
                    verifyUserDetailsListener.onFailure(VerifyException("" + t))

                }

                override fun onResponse(call: Call<VerifyPersonResponse>?, response: Response<VerifyPersonResponse>?) {
                    Log.d("VerifySdkErrorLog", response.toString() + " Token  " + token)
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success!!) {
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
                            verifyUserDetailsListener.onFailure(VerifyException(response.toString()))

                        }
                    } else {
                        verifyUserDetailsListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })
        }

    }

    private fun loadPerson(id: String, userDetailsListener: GetUserDetailsListener) {

        if (!NetworkUtils.isConnected(context)) {
            userDetailsListener.onFailure(VerifyException("No internet connection"))
            return
        }
        userDetailsListener.onCallStarted()

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl()).searchIdNumber(id)
            call.enqueue(object : Callback<SearchUserResponse> {
                override fun onFailure(call: Call<SearchUserResponse>?, t: Throwable?) {
                    Log.d("VerifySdkErrorLog", "" + t)
                    userDetailsListener.onFailure(VerifyException("" + t))

                }

                override fun onResponse(call: Call<SearchUserResponse>?, response: Response<SearchUserResponse>?) {
                    Log.d("VerifySdkErrorLog", response.toString() + " Token  " + token)
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success!!) {
                                userDetailsListener.onResponse(response.body()?.data!!)
                            } else {
                                userDetailsListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        "",
                                        response.body()?.errors
                                    )
                                )

                            }
                        } else {
                            userDetailsListener.onFailure(VerifyException(response.toString()))

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

        if (s == null) {
            searchNcaContractorByIdListener.onFailure(VerifyException("Registration number cannot be null"))
            return
        }
        if (searchNcaContractorByIdListener == null) {
            searchNcaContractorByIdListener.onFailure(VerifyException("Not listener set"))
            return
        }


        if (token != null) {

            loadNcaContractor(s, searchNcaContractorByIdListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    tokenCallListener?.onTokenCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    tokenCallListener?.onTokenRecieved(token)
                    loadNcaContractor(s, searchNcaContractorByIdListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    tokenCallListener?.onTokenCallFailed(verifyException)

                }
            })

        }


    }

    private fun loadNcaContractor(s: String, searchNcaContractorByIdListener: SearchNcaContractorByIdListener) {
        if (!NetworkUtils.isConnected(context)) {
            searchNcaContractorByIdListener.onFailure(VerifyException("No internet connection"))
            return
        }
        searchNcaContractorByIdListener.onCallStarted()

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl()).searchNcaContractorById(s)
            call.enqueue(object : Callback<SearchNcaContractorResponse> {
                override fun onFailure(call: Call<SearchNcaContractorResponse>?, t: Throwable?) {
                    Log.d("VerifySdkErrorLog", t.toString() + " Token  " + token)

                    searchNcaContractorByIdListener.onFailure(VerifyException("" + t?.message))

                }

                override fun onResponse(
                    call: Call<SearchNcaContractorResponse>?,
                    response: Response<SearchNcaContractorResponse>?
                ) {
                    Log.d("VerifySdkErrorLog", Gson().toJson(response) + " Token  " + token)
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success!!) {
                                searchNcaContractorByIdListener.onResponse(
                                    response.body()?.data!!,
                                    response.body()?.data!!.verified!!,
                                    response.body()?.data!!.status!!
                                )
                            } else {
                                searchNcaContractorByIdListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        "",
                                        response.body()?.errors
                                    )
                                )

                            }
                        } else {
                            searchNcaContractorByIdListener.onFailure(VerifyException(response.message()))

                        }
                    } else {
                        searchNcaContractorByIdListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })
        }


    }

    private fun loadNcaContractors(s: String, searchNcaContractorByNameListener: SearchNcaContractorByNameListener) {
        if (!NetworkUtils.isConnected(context)) {
            searchNcaContractorByNameListener.onFailure(VerifyException("No internet connection"))
            return
        }
        searchNcaContractorByNameListener.onCallStarted()

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl()).searchNcaContractorByName(s)
            call.enqueue(object : Callback<SearchNcaContractorsResponse> {
                override fun onFailure(call: Call<SearchNcaContractorsResponse>?, t: Throwable?) {
                    Log.d("VerifySdkErrorLog", t.toString() + " Token  " + token)

                    searchNcaContractorByNameListener.onFailure(VerifyException("" + t?.message))

                }

                override fun onResponse(
                    call: Call<SearchNcaContractorsResponse>?,
                    response: Response<SearchNcaContractorsResponse>?
                ) {
                    Log.d("VerifySdkErrorLog", Gson().toJson(response) + " Token  " + token)
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success!!) {
                                searchNcaContractorByNameListener.onResponse(response.body()?.data!!)
                            } else {
                                searchNcaContractorByNameListener.onFailure(
                                    VerifyException(
                                        response.body()?.message,
                                        "",
                                        response.body()?.errors
                                    )
                                )

                            }
                        } else {
                            searchNcaContractorByNameListener.onFailure(VerifyException(response.message()))

                        }
                    } else {
                        searchNcaContractorByNameListener.onFailure(VerifyException("Could Not Load Data"))

                    }

                }
            })
        }


    }

    fun searchNcaContractorByName(s: String?, searchNcaContractorByNameListener: SearchNcaContractorByNameListener) {
        if (s == null) {
            searchNcaContractorByNameListener.onFailure(VerifyException("Name cannot be null"))
            return
        }
        if (searchNcaContractorByNameListener == null) {
            searchNcaContractorByNameListener.onFailure(VerifyException("Not listener set"))
            return
        }


        if (token != null) {

            loadNcaContractors(s, searchNcaContractorByNameListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    tokenCallListener?.onTokenCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    tokenCallListener?.onTokenRecieved(token)
                    loadNcaContractors(s, searchNcaContractorByNameListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    tokenCallListener?.onTokenCallFailed(verifyException)

                }
            })

        }


    }

    fun verifyNcaContractor(
        verifyNcaContractor: VerifyNcaContractor,
        verifyNcaContractorListener: verifyNcaContractorListener
    ) {


        if (verifyNcaContractor.registration_no == null
            && verifyNcaContractor.contractor_name == null
            && verifyNcaContractor.town == null
            && verifyNcaContractor.category == null
            && verifyNcaContractor.contractor_class == null


        ) {
            verifyNcaContractorListener.onFailure(VerifyException("Must have atleast one field not null"))
            return
        }

        if (token != null) {

            loadverifyNcaContractor(verifyNcaContractor, verifyNcaContractorListener)


        } else {
            generateToken(object : TokenCallListener {
                override fun onTokenCallStarted() {
                    tokenCallListener?.onTokenCallStarted()

                }

                override fun onTokenRecieved(token: Token) {
                    tokenCallListener?.onTokenRecieved(token)
                    loadverifyNcaContractor(verifyNcaContractor, verifyNcaContractorListener)

                }

                override fun onTokenCallFailed(verifyException: VerifyException) {
                    tokenCallListener?.onTokenCallFailed(verifyException)

                }
            })

        }

    }

    fun loadverifyNcaContractor(
        verifyNcaContractor: VerifyNcaContractor,
        verifyNcaContractorListener: verifyNcaContractorListener
    ) {
        if (!NetworkUtils.isConnected(context)) {
            verifyNcaContractorListener.onFailure(VerifyException("No internet connection"))
            return
        }
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(token!!, getBaseUrl())
                .verifyNcaContractor(verifyNcaContractor.registration_no!!, verifyNcaContractor)
            call.enqueue(object : Callback<VerifyNcaContractorResponse> {
                override fun onFailure(call: Call<VerifyNcaContractorResponse>?, t: Throwable?) {
                    Log.d("VerifySdkErrorLog", "" + t)
                    verifyNcaContractorListener.onFailure(VerifyException("" + t))

                }

                override fun onResponse(
                    call: Call<VerifyNcaContractorResponse>?,
                    response: Response<VerifyNcaContractorResponse>?
                ) {
                    Log.d("VerifySdkErrorLog", response.toString() + " Token  " + token)
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.success!!) {
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
                            verifyNcaContractorListener.onFailure(VerifyException(response.toString()))

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
        var secretKey: String? = null,
        var handleTokenInternally: Boolean? = true,
        var tokenCallListener: TokenCallListener? = null
    ) {

        fun enviroment(enviroment: Enviroment) = apply { this.enviroment = enviroment }
        fun consumerKey(consumerKey: String?) = apply { this.consumerKey = consumerKey }
        fun secretKey(secretKey: String) = apply { this.secretKey = secretKey }
        fun setContext(context: Context) = apply { this.context = context }
        fun handleTokenInternally(handleTokenInternally: Boolean?, tokenCallListener: TokenCallListener) =

            apply {
                this.handleTokenInternally = handleTokenInternally
                this.tokenCallListener = tokenCallListener

            }


        fun build() = Verify(context, enviroment, consumerKey, secretKey, handleTokenInternally, tokenCallListener)


    }


}