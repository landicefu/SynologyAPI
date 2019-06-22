package tw.lifehackers.synology.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import tw.lifehackers.synology.model.PingResult
import tw.lifehackers.synology.model.downloadstation.DownloadTaskResult

interface SynologyApi {
    @GET("/webman/pingpong.cgi?action=cors")
    fun ping(): Single<PingResult>

    @GET("/webapi/DownloadStation/task.cgi?api=SYNO.DownloadStation.Task&version=1&method=list")
    fun listDownloadTasks(
            @Query("offset") offset: Int? = null,
            @Query("limit") limit: Int? = null,
            @Query("additional") additionalFields: String? = null
    ): Single<DownloadTaskResult>

    companion object {
        fun getAdditionalFields(detail: Boolean, transfer: Boolean): String? {
            if (!detail && !transfer) return null
            val options = mutableListOf<String>().apply {
                if (detail) add("detail")
                if (transfer) add("transfer")
            }
            return options.joinToString(",")
        }
    }
}