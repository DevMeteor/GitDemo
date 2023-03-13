package cn.devmeteor.gitdemo.systemplatform

import cn.devmeteor.gitdemo.command.exception.CommandFailException
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*

class Windows : SystemPlatform() {

    override fun getName(): String = "Windows"

    override fun execCommand(cmd: String, dir: File?, env: Array<String>?): String =
        try {
            val process = Runtime.getRuntime().exec(cmd, env, dir)
            val sb = StringBuilder()
            val scanner = Scanner(process.inputStream)
            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                sb.append(line).append("\n")
            }
            sb.removeSuffix("\n").toString()
        } catch (e: Exception) {
            throw CommandFailException(cmd)
        }


    override fun execCommandInDirFileInStream(
        cmd: String,
        inputStream: InputStream,
        dir: File?,
        env: Array<String>?
    ): ByteArray =
        try {
            val process = Runtime.getRuntime().exec(cmd, env, dir)
            process.outputStream.write(inputStream.readBytes())
            process.outputStream.flush()
            process.outputStream.close()
            process.inputStream.readBytes()
        } catch (e: IOException) {
            throw CommandFailException(cmd)
        }
}