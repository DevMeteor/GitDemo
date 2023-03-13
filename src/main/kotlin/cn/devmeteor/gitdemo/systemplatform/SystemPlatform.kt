package cn.devmeteor.gitdemo.systemplatform

import java.io.File
import java.io.InputStream

abstract class SystemPlatform {

    abstract fun getName(): String

    abstract fun execCommand(
        cmd: String,
        dir: File? = null,
        env: Array<String>? = null
    ): String

    fun execCommandInDirFile(
        cmd: String,
        dir: String? = null,
        env: Array<String>? = null
    ): String =
        execCommand(cmd, if (dir != null) File(dir) else null, env)

    abstract fun execCommandInDirFileInStream(
        cmd: String,
        inputStream: InputStream,
        dir: File? = null,
        env: Array<String>? = null
    ): ByteArray

    fun execCommandInStream(
        cmd: String,
        inputStream: InputStream,
        dir: String? = null,
        env: Array<String>? = null
    ): ByteArray =
        execCommandInDirFileInStream(cmd, inputStream, if (dir != null) File(dir) else null, env)

    enum class Type(val systemPlatform: SystemPlatform) {
        TypeWindows(Windows()),
        //TypeLinux(Linux())
    }
}