package tw.lifehackers.test

import com.google.gson.GsonBuilder

private val prettyGson = GsonBuilder().setPrettyPrinting().create()

fun Any.toPrettyJson() = prettyGson.toJson(this)!!
