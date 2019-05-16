import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kogi
 */
object RequestService {
    var gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getRetrofit(token: String, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient(token, "", ""))
            .build()
    }

    private fun getRetrofit(baseUrl: String, consumerKey: String, secretKey: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient("", consumerKey, secretKey))
            .build()
    }

    private fun getClient(token: String, consumerKey: String, secretKey: String): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader("consumerKey", consumerKey)
                .addHeader("secretKey", secretKey)
                .build()
            chain.proceed(newRequest)
        }.build()
    }


    fun getService(token: String, baseUrl: String): EndPoints {
        return getRetrofit(token, baseUrl).create(EndPoints::class.java)
    }

    fun getService(consumerKey: String, secretKey: String, baseUrl: String): EndPoints {
        return getRetrofit(baseUrl, consumerKey, secretKey).create(EndPoints::class.java)
    }


}
