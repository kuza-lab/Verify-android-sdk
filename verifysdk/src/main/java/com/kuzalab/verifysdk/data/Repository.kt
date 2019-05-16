import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {


    fun generateToken(id: String) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(id, "").generateToken()
            call.enqueue(object : Callback<AuthToken> {
                override fun onFailure(call: Call<AuthToken>?, t: Throwable?) {
                }

                override fun onResponse(
                    call: Call<AuthToken>?,
                    response: Response<AuthToken>?
                ) {

                }
            })
        }
    }

    fun verifyNationalId(id: String) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(id, "").generateToken()
            call.enqueue(object : Callback<AuthToken> {
                override fun onFailure(call: Call<AuthToken>?, t: Throwable?) {
                }

                override fun onResponse(
                    call: Call<AuthToken>?,
                    response: Response<AuthToken>?
                ) {

                }
            })
        }
    }
}