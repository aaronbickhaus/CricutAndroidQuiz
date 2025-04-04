package com.cricut.common.logger

enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

interface Logger {
    var level: LogLevel

    fun debug(message: String)
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String, throwable: Throwable? = null)
}

object LoggerImpl : Logger {

    override var level: LogLevel = LogLevel.DEBUG

    override fun debug(message: String) {
        if (level.ordinal <= LogLevel.DEBUG.ordinal) {
            println("[DEBUG] $message")
        }
    }

    override fun info(message: String) {
        if (level.ordinal <= LogLevel.INFO.ordinal) {
            println("[INFO] $message")
        }
    }

    override fun warn(message: String) {
        if (level.ordinal <= LogLevel.WARN.ordinal) {
            println("[WARN] $message")
        }
    }

    override fun error(message: String, throwable: Throwable?) {
        if (level.ordinal <= LogLevel.ERROR.ordinal) {
            println("[ERROR] $message")
            throwable?.printStackTrace()
        }
    }
}