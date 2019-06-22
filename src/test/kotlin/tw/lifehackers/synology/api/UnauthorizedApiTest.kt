package tw.lifehackers.synology.api

import org.junit.Before
import org.junit.Test
import tw.lifehackers.synology.SynologyNAS
import tw.lifehackers.test.resultPrintingObserver

class UnauthorizedApiTest {

    private lateinit var api: UnauthenticatedApi

    @Before
    fun init() {
        val nas = SynologyNAS(QUICK_CONNECT_ID, USER_NAME, PASSWORD, true)
        api = nas.unauthenticatedApi
    }

    @Test
    fun authenticationTest() {
        api.auth(USER_NAME, PASSWORD)
                .subscribe(resultPrintingObserver)
    }

    @Test
    fun pingTest() {
        api
                .ping()
                .subscribe(resultPrintingObserver)
    }
}