package cn.devmeteor.gitdemo.command

import cn.devmeteor.gitdemo.Config
import java.io.InputStream

class ReceivePackCommand private constructor(): Command<ByteArray> {

    companion object {
        fun newInstance() = ReceivePackCommand()
    }

    private var repoPath: String? = null
    private var input: InputStream? = null

    fun repoPath(path: String): ReceivePackCommand {
        repoPath = path
        return this
    }

    fun input(inputStream: InputStream): ReceivePackCommand {
        input = inputStream
        return this
    }

    override fun execute(): ByteArray {
        repoPath.checkArgNotNull("repoPath")
        input.checkArgNotNull("inputStream")
        return Config.getPlatform().execCommandInStream("git receive-pack --stateless-rpc $repoPath", input!!)
    }
}