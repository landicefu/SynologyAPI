package tw.lifehackers.synology.api

import org.junit.Test
import tw.lifehackers.synology.SynologyNAS
import tw.lifehackers.test.resultPrintingObserver

class SynologyApiTest {

    companion object {
        private val api = SynologyNAS(QUICK_CONNECT_ID, USER_NAME, PASSWORD, true).getApi()
    }

    @Test
    fun testDownloadListApi() {
        api.flatMap { synologyApi -> synologyApi.listDownloadTasks() }
                .subscribe(resultPrintingObserver)
    }

    @Test
    fun testDownloadListApi_WithOffsetAndLimit() {
        api.flatMap { synologyApi -> synologyApi.listDownloadTasks(5, 3) }
                .subscribe(resultPrintingObserver)
    }

    @Test
    fun testDownloadListApi_WithAdditionFields() {
        api.flatMap { synologyApi -> synologyApi.listDownloadTasks(0, 1, SynologyApi.getAdditionalFields(true, false)) }
                .subscribe(resultPrintingObserver)
    }
}