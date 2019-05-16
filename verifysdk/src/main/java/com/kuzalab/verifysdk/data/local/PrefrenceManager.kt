import android.content.Context
import android.content.SharedPreferences

class PrefrenceManager(internal var _context: Context) {

    private var pref: SharedPreferences

    private var editor: SharedPreferences.Editor

    private var PRIVATE_MODE = 0


    companion object {

        private val OAUTH_TOKEN = "verify_sdk_token"
        private val PREF_NAME = "verify_sdk_prefrences"

    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun clearUser() {
        editor.clear()
        editor.commit()
    }

    fun setToken(token: String) {
        editor.putString(OAUTH_TOKEN, token)
        editor.commit()
    }

    fun getToken(): String {
        return pref.getString(OAUTH_TOKEN, "")!!
    }


}