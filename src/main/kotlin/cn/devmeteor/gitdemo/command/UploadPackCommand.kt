package cn.devmeteor.gitdemo.command

import cn.devmeteor.gitdemo.Config
import java.io.InputStream

class UploadPackCommand private constructor() : Command<ByteArray> {

    companion object {
        fun newInstance() = UploadPackCommand()
    }

    private var repoPath: String? = null
    private var input: InputStream? = null

    fun repoPath(path: String): UploadPackCommand {
        repoPath = path
        return this
    }

    fun input(inputStream: InputStream): UploadPackCommand {
        input = inputStream
        return this
    }

    override fun execute(): ByteArray {
        repoPath.checkArgNotNull("repoPath")
        input.checkArgNotNull("inputStream")
        return Config.getPlatform().execCommandInStream("git upload-pack --stateless-rpc $repoPath", input!!)
    }
}