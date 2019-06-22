package tw.lifehackers.synology.model

import com.google.gson.annotations.SerializedName

data class PingResult(
        @SerializedName("boot_done") val bootDone: Boolean,
        @SerializedName("disk_hibernation") val diskHibernation: Boolean,
        val ezid: String?,
        val success: Boolean

)