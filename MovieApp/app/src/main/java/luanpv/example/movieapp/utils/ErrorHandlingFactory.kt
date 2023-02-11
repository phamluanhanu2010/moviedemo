package luanpv.example.movieapp.utils

import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class BaseException(
    val errorType: ErrorType,
    val serverErrorResponse: ServerResponseEntity? = null,
    val response: Response<*>? = null,
    val ErrorCode: String? = null,
    var url: String? = null,
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    override val message: String?
        get() = when (errorType) {
            ErrorType.HTTP -> response?.message()
            ErrorType.NETWORK -> cause?.message
            ErrorType.UNEXPECTED -> cause?.message
            else -> serverErrorResponse?.ErrorMessage
        }

    companion object {
        fun toHttpError(response: Response<*>?, url: String, code: String?) =
            BaseException(
                errorType = ErrorType.HTTP,
                response = response,
                ErrorCode = code,
                url = url
            )

        fun toNetworkError(cause: Throwable, url: String, code: String?) =
            BaseException(
                errorType = ErrorType.NETWORK,
                cause = cause,
                ErrorCode = code,
                url = url
            )

        fun toServerError(serverErrorResponse: ServerResponseEntity, url: String, code: String?, response: Response<*>?) =
            BaseException(
                errorType = ErrorType.SERVER,
                serverErrorResponse = serverErrorResponse,
                ErrorCode = code,
                url = url,
                response = response
            )

        fun toUnexpectedError(cause: Throwable, url: String, code: String?) =
            BaseException(
                errorType = ErrorType.UNEXPECTED,
                cause = cause,
                ErrorCode = code,
                url = url
            )
    }
}

/**
 * Identifies the error type which triggered a [BaseException]
 */
enum class ErrorType {
    /**
     * An [IOException] occurred while communicating to the server.
     */
    NETWORK,

    /**
     * A non-2xx HTTP status code was received from the server.
     */
    HTTP,

    /**
     * A error server with code & message
     */
    SERVER,

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}

abstract class ResponseEntity {
    @SerializedName("ErrorCode")
    val ErrorCode: String? = null
    @SerializedName("ErrorMessage")
    val ErrorMessage: String? = null
    var source: String? = null
    var url: String? = null

    @SerializedName("responseHeader")
    val responseHeader: String? = null
    @SerializedName("hashResponseHeader")
    val hashResponseHeader: String? = null
     @SerializedName("resBody")
     val resBody: String? = null
    @SerializedName("hashResBody")
    val hashResBody: String? = null
    @SerializedName("resBodyBase64")
    val resBodyBase64: String? = null
    @SerializedName("hashResBodyBase64")
    val hashResBodyBase64: String? = null
    fun isSuccess(): Boolean {
        return "0" == ErrorCode
    }
}

class ServerResponseEntity : ResponseEntity()

