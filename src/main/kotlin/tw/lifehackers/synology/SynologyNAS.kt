package tw.lifehackers.synology

import io.reactivex.Single
import me.oldjing.quickconnect.QuickConnectInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import tw.lifehackers.synology.exceptions.UnauthorizedException
import tw.lifehackers.synology.api.SynologyApi
import tw.lifehackers.synology.api.UnauthenticatedApi
import tw.lifehackers.synology.model.AuthResult
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class SynologyNAS(
        val quickConnectUserName: String,
        val nasUserName: String,
        val nasPassword: String,
        val httpsEnabled: Boolean
) {

    val scheme get() = if (httpsEnabled) "https" else "http"

    val okHttpClient = OkHttpClient.Builder().apply {
        val trustManager = DefaultTrustManager()
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, arrayOf<TrustManager>(trustManager), SecureRandom())
        }
        addInterceptor(QuickConnectInterceptor())
        sslSocketFactory(sslContext.socketFactory, trustManager)
        hostnameVerifier(HostnameVerifier { _, _ ->
            return@HostnameVerifier true
        })

        addInterceptor { chain ->
            var request = chain.request()
            sid?.let {
                val url = request.url().newBuilder().addQueryParameter("_sid", it).build()
                request = request.newBuilder().url(url).build()
            }
            chain.proceed(request)
        }
    }.build()

    val retrofit = Retrofit.Builder()
            .baseUrl("$scheme://$quickConnectUserName")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private var api = retrofit.create(SynologyApi::class.java)

    var sid: String? = null
        private set

    val authenficated get() = sid != null

    fun authorize(): Single<AuthResult> = unauthenticatedApi.auth(nasUserName, nasPassword)
            .doOnSuccess { authResult ->
                if (!authResult.success) {
                    throw UnauthorizedException("authentication failure")
                }
                sid = authResult.sid
            }

    fun getApi(): Single<SynologyApi> = Single.just(Unit)
            .flatMap {
                val sid = sid
                if (sid == null) authorize().map { api }
                else Single.just(api)
            }

    val unauthenticatedApi get() = retrofit.create(UnauthenticatedApi::class.java)
}

private class DefaultTrustManager : X509TrustManager {
    override fun checkClientTrusted(arg0: Array<out X509Certificate>?, arg1: String?) = Unit

    override fun checkServerTrusted(arg0: Array<out X509Certificate>?, arg1: String?) = Unit

    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}