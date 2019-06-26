package tw.lifehackers.synology.api

import org.junit.Test
import tw.lifehackers.synology.SynologyNAS
import tw.lifehackers.test.resultPrintingObserver
import tw.lifehackers.test.toPrettyJson

class SynologyApiTest {

    companion object {
        private val nas = SynologyNAS(QUICK_CONNECT_ID, USER_NAME, PASSWORD, true)
        private val api = nas.getApi()
        private const val TEST_MAGNETIC_LINK = "magnet:?xt=urn:btih:7b44c9a7be58f00f88c1b132d00ffa14ec999f8a&dn=Stunning+Car+Wallpapers+%7BPack-89%7D&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969"
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

    @Test
    fun testDownloadApi_magnetLink() {
        val testObserver = api
                .flatMap { synologyApi ->
                    println("Authorization succeeded")
                    synologyApi.download(TEST_MAGNETIC_LINK)
                }.doOnSuccess { result -> println(result.toPrettyJson()) }
                .test()
        testObserver.assertComplete()
        testObserver.assertValue { result -> result.success }
    }
}