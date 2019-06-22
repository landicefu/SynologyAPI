package tw.lifehackers.synology.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import tw.lifehackers.synology.model.AuthResult
import tw.lifehackers.synology.model.PingResult

interface UnauthenticatedApi {
    @GET("/webman/pingpong.cgi?action=cors")
    fun ping(): Single<PingResult>

    @GET("/webapi/auth.cgi?api=SYNO.API.Auth&version=2&method=login&session=DownloadStation&format=cookie")
    fun auth(@Query("account") account: String, @Query("passwd") password: String): Single<AuthResult>
}