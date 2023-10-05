package com.example.starter

import io.jooby.kt.runApp

fun main(args: Array<String>) {
    runApp(args) {

        get("/") { "Hello sdfJooby!" }

    }
}