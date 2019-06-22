package tw.lifehackers.synology.model.downloadstation

import com.google.gson.annotations.SerializedName

data class DownloadTaskResult(
        val success: Boolean,
        val data: DownloadTasks
)

data class DownloadTasks(
        val offset: Int,
        val total: Int,
        val tasks: List<DownloadTask>
)

data class DownloadTask(
        val id: String,
        val size: Long,
        val status: String,
        val title: String,
        val type: String,
        val username: String,
        @SerializedName("additional") val additionalData: AdditionalData?
)

data class AdditionalData(
        val detail: Detail?,
        val transfer: Transfer?
)

data class Detail(
        @SerializedName("create_time") val createTime: Long,
        @SerializedName("started_time") val startedTime: Long,
        @SerializedName("completed_time") val completedTime: Long,
        @SerializedName("waiting_seconds") val waitingSeconds: Long,
        val seedelapsed: Long,

        @SerializedName("connected_peers") val connectedPeers: Int,
        @SerializedName("total_peers") val totalPeers: Int,
        @SerializedName("connected_leechers") val connectedLeechers: Int,
        @SerializedName("connected_seeders") val connectedSeeders: Int,

        val uri: String,
        val destination: String,
        @SerializedName("total_pieces") val totalPieces: Int,
        @SerializedName("unzip_password") val unzipPassword: String
)

data class Transfer(
    @SerializedName("downloaded_pieces") val downloadedPieces: Int,
    @SerializedName("size_downloaded") val sizeDownloaded: Long,
    @SerializedName("size_uploaded") val sizeUploaded: Long,
    @SerializedName("speed_download") val speedDownload: Long,
    @SerializedName("speed_upload") val speedUpload: Long
)