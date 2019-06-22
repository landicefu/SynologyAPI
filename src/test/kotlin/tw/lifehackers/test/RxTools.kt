package tw.lifehackers.test

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

val resultPrintingObserver = object : SingleObserver<Any> {
    override fun onSubscribe(d: Disposable) = Unit
    override fun onSuccess(t: Any) = println("${t.javaClass.simpleName} ${t.toPrettyJson()}")
    override fun onError(e: Throwable) = println(e)
}