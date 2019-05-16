import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kuzalab.verifysdk.models.Person


class SearchUserResponse {
    @SerializedName("status_code")
    @Expose
    val statusCode: Int? = null
    @SerializedName("status_name")
    @Expose
    val statusName: String? = null
    @SerializedName("success")
    @Expose
    val success: Boolean? = null
    @SerializedName("message")
    @Expose
    val message: String? = null
    @SerializedName("result_code")
    @Expose
    val resultCode: Int? = null
    @SerializedName("data")
    @Expose
    var data: Person? = null
    @SerializedName("errors")
    @Expose
    val errors: List<String>? = null
    @SerializedName("total_records")
    @Expose
    val totalRecords: Int? = null

}

